package pl.allegro.follower.util.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pl.allegro.follower.DI.component.DaggerItemPropertiesComponent
import pl.allegro.follower.DI.service.AllegroService
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.model.repository.ItemRepository
import pl.allegro.follower.util.textToFloat
import pl.allegro.follower.view.viewmodel.MainActivityViewModel
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ItemStateService : Service() {

    companion object{
        const val TAG="ItemStateService"
    }

    @Inject
    lateinit var allegroService:AllegroService

    private lateinit var itemRepository: ItemRepository
    private val compositeDisposable = CompositeDisposable()

    init {
        DaggerItemPropertiesComponent.builder().build().inject(this)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        itemRepository = ItemRepository(application)

        itemRepository
            .getObservableAllItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Observer<List<Item>>{
                override fun onComplete() {
                    Log.d(TAG,"On complete invoked")
                }
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
                override fun onNext(t: List<Item>) {
                    checkItemsHasChanged(t)
                }
                override fun onError(e: Throwable) {
                    e.fillInStackTrace()
                    Log.d(TAG,"On error invoked ${e.message.toString()}")
                }
            })

        return START_NOT_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun checkItemsHasChanged(allegroItems: List<Item>) {
        Observable
            .fromIterable(allegroItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                val itemChanged = compareItems(Jsoup.connect(it.itemURL.toString()).get(), it)
                if (itemChanged) {
                    itemRepository.updateItem(it)
                }
                itemChanged
            }
            .delay(1, TimeUnit.SECONDS)
            .subscribe(object : Observer<Item> {
                override fun onComplete() {
                    Log.d(MainActivityViewModel.TAG, "On Complete invoked")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: Item) {

                    //todo notify user
                }

                override fun onError(e: Throwable) {
                    Log.d(MainActivityViewModel.TAG, e.message.toString())
                }
            })
    }

    private fun compareItems(document: Document, item: Item): Boolean {

        val dateFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US)


        val docPrice: String = document.selectFirst(allegroService.pricePath).text()

        var expiredIn: String? = ""

        if (document.selectFirst(allegroService.expiredInPath) != null)
            expiredIn = document.selectFirst(allegroService.expiredInPath).text()

        return try {
            val floatPrice: Float = textToFloat(docPrice)

            item.expiredIn = expiredIn
            item.lastUpdate = dateFormatter.format(Date())

            if (floatPrice != item.itemPrice) {
                item.itemPrice = floatPrice
                true
            } else false

        } catch (e: NumberFormatException) {
            e.fillInStackTrace()
            false
        }
    }

    private fun buildNotifcation(item:Item){

        val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val mNotifyChannel = NotificationChannel("","",NotificationManager.IMPORTANCE_DEFAULT)
            mNotifyChannel.description = ""
            notificationManager.createNotificationChannel(mNotifyChannel)
        }

        val mNotifyBuilder = NotificationCompat.Builder(this, "")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent:Intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(item.itemURL)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}