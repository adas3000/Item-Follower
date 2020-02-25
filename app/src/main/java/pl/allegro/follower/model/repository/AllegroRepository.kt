package pl.allegro.follower.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import pl.allegro.follower.model.data.CompareItems
import pl.allegro.follower.model.data.Item

class AllegroRepository  {

    val itemCompareLiveData:MutableLiveData<List<CompareItems>> = MutableLiveData()




    companion object{
        private const val TAG="AllegroRepository"
    }

    fun getItems(allegroItems:List<Item>){

        val compareList:MutableList<CompareItems> = mutableListOf()

        Observable
            .fromIterable(allegroItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map CompareItems(Jsoup.connect(it.itemURL.toString()).get(),it)
            }
            .subscribe(object:Observer<CompareItems>{
                override fun onComplete() {
                    itemCompareLiveData.value = compareList
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




}