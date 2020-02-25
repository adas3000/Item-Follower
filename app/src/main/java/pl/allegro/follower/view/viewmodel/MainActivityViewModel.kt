package pl.allegro.follower.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pl.allegro.follower.component.DaggerItemPropertiesComponent
import pl.allegro.follower.model.data.CompareItems
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.util.textToFloat
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class MainActivityViewModel : ViewModel() {

    companion object{
        const val TAG="MainActivityViewModel"
    }

    val liveDataItem:MutableLiveData<Item> = MutableLiveData()

    @Inject
    @Named("title")
    lateinit var titlePath: String
    @Inject
    @Named("price")
    lateinit var pricePath: String
    @Named("img")
    @Inject
    lateinit var imgPath: String
    @Named("expiredIn")
    @Inject
    lateinit var expiredInPath: String

    init {
        DaggerItemPropertiesComponent.builder().build().inject(this)
    }


    fun checkItemsHasChanged(allegroItems:List<Item>){

        val compareList:MutableList<CompareItems> = mutableListOf()
        
        Observable
            .fromIterable(allegroItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map CompareItems(Jsoup.connect(it.itemURL.toString()).get(),it)
            }
            .subscribe(object: Observer<CompareItems> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CompareItems) {
                    compareList.add(t)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,e.message.toString())
                }
            })
    }

    private fun compareItems(document: Document,item:Item):Boolean{

        val dateFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US)


        val docPrice: String = document.selectFirst(pricePath).text()

        var expiredIn: String? = ""

        if (document.selectFirst(expiredIn) != null)
            expiredIn = document.selectFirst(expiredIn).text()

        try {
            val floatPrice: Float = textToFloat(docPrice)

            item.expiredIn = expiredIn
            item.lastUpdate = dateFormatter.format(Date())
            item.itemPrice = floatPrice

            //update item

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