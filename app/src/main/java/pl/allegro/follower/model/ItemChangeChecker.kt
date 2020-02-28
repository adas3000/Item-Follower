package pl.allegro.follower.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
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
import pl.allegro.follower.util.service.ItemStateService
import pl.allegro.follower.util.textToFloat
import pl.allegro.follower.view.viewmodel.MainActivityViewModel
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ItemChangeChecker(private val itemRepository: ItemRepository,private val context: Context) {

    @Inject
    lateinit var allegroService: AllegroService
    private val compositeDisposable = CompositeDisposable()

    companion object{
        const val TAG="ItemChangeChecker"
    }

    init {
        DaggerItemPropertiesComponent.builder().build().inject(this)
    }

    fun findItems(){
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
                    Log.d(TAG, "On Complete invoked")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: Item) {
                    buildNotification(t)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, e.message.toString())
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

    private fun buildNotification(item: Item){

        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val mNotifyChannel = NotificationChannel(context.getString(R.string.app_notify_channel_id_text),
                context.getString(R.string.app_notify_channel_name_text),
                NotificationManager.IMPORTANCE_DEFAULT)
            mNotifyChannel.description = context.getString(R.string.app_notify_channel_description_text)
            notificationManager.createNotificationChannel(mNotifyChannel)
        }

        val contentText = context.getString(R.string.app_notify_content_text,item.itemName,item.expiredIn)

        val mNotifyBuilder: NotificationCompat.Builder = NotificationCompat
            .Builder(context, context.getString(R.string.app_notify_channel_id_text))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(item.itemURL)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context,
            0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mNotifyBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(item.uid,mNotifyBuilder.build())
    }

    fun clear(){
        compositeDisposable.clear()
    }

}