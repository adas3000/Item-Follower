package pl.allegro.follower.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import pl.allegro.follower.DI.component.DaggerItemPropertiesComponent
import pl.allegro.follower.DI.AllegroInfo
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.util.textToFloat
import pl.allegro.follower.view.ui.AddItemActivity.Companion.TAG
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddViewModel : ViewModel(){

    val itemNameValue:MutableLiveData<String> = MutableLiveData()
    val itemUrlValue:MutableLiveData<String> = MutableLiveData()

    val itemValue:MutableLiveData<Item> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var allegroInfo: AllegroInfo

    init {
        DaggerItemPropertiesComponent.builder().build().inject(this)
    }

    fun setItemName(name:String){
        itemNameValue.postValue(name)
    }

    fun setItemUrl(url:String){
        itemUrlValue.postValue(url)
    }

    fun findItemData(url:String,name:String){

        Observable
            .create(ObservableOnSubscribe<Item> { emitter ->
                if(!emitter.isDisposed){

                    val doc = Jsoup.connect(url).get()

                    val title: String =
                        if (name.isNotEmpty()) name
                        else
                            doc.selectFirst(allegroInfo.titlePath).text()

                    val strPrice: String = doc.selectFirst(allegroInfo.pricePath).text()
                    val imgUrl: String = doc.selectFirst(allegroInfo.imgPath).absUrl("src")

                    var expiredIn: String? = ""
                    if (doc.selectFirst(allegroInfo.expiredInPath) != null)
                        expiredIn = doc.selectFirst(allegroInfo.expiredInPath).text()

                    try {
                        val floatPrice: Float = textToFloat(strPrice)
                        val item = Item(0,title,floatPrice,doc.location())
                        val dateFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US)

                        item.itemImgUrl = imgUrl
                        item.expiredIn = expiredIn
                        item.lastUpdate = dateFormatter.format(Date())
                        emitter.onNext(item)
                        emitter.onComplete()

                    } catch (e: NumberFormatException) {
                        e.fillInStackTrace()
                        emitter.onError(Throwable(e.message.toString()))
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Observer<Item>{
                override fun onComplete() {
                    Log.d(TAG,"On complete invoked")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,"On Error invoked ${e.message.toString()}")
                }

                override fun onNext(t: Item) {
                    itemValue.postValue(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
            })


    }

    fun onDestroy(){
        compositeDisposable.clear()
    }



}