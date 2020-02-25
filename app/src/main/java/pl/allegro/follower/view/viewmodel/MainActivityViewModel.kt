package pl.allegro.follower.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pl.allegro.follower.DI.component.DaggerItemPropertiesComponent
import pl.allegro.follower.DI.service.AllegroService
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.util.textToFloat
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    companion object{
        const val TAG="MainActivityViewModel"
    }

    val liveDataItem:MutableLiveData<Item> = MutableLiveData()

    @Inject
    lateinit var allegroService:AllegroService


    init {
        DaggerItemPropertiesComponent.builder().build().inject(this)
    }


    fun checkItemsHasChanged(allegroItems:List<Item>){
        Observable
            .fromIterable(allegroItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter{
                val itemChanged = compareItems(Jsoup.connect(it.itemURL.toString()).get(),it)
                if(itemChanged){} //update
                itemChanged
            }
            .delay(1,TimeUnit.SECONDS)
            .subscribe(object:Observer<Item>{
                override fun onComplete() {
                    Log.d(TAG,"On Complete invoked")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Item) {
                    liveDataItem.value = t
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,e.message.toString())
                }
            })

    }

    private fun compareItems(document: Document,item:Item):Boolean{

        val dateFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US)


        val docPrice: String = document.selectFirst(allegroService.pricePath).text()

        var expiredIn: String? = ""

        if (document.selectFirst(allegroService.expiredInPath) != null)
            expiredIn = document.selectFirst(allegroService.expiredInPath).text()

        try {
            val floatPrice: Float = textToFloat(docPrice)

            item.expiredIn = expiredIn
            item.lastUpdate = dateFormatter.format(Date())

            return if (floatPrice != item.itemPrice) {
                item.itemPrice = floatPrice
                true
            } else false

        }
        catch (e: NumberFormatException) {
            e.fillInStackTrace()
            return false
        }
    }





}