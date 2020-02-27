package pl.allegro.follower.view.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pl.allegro.follower.DI.component.DaggerItemPropertiesComponent
import pl.allegro.follower.DI.service.AllegroService
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.model.repository.ItemRepository
import pl.allegro.follower.util.textToFloat
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "MainActivityViewModel"
        const val BCKGRD_CHECK_ENABLED =  "background_check"
    }

    val itemListLiveData: LiveData<List<Item>>

    private val itemRepository: ItemRepository = ItemRepository(application)

    init {
        itemListLiveData = itemRepository.getAllItems()
    }


    fun insertItem(item: Item) {
        itemRepository.insertItem(item)
    }

    fun updateItem(item: Item) {
        itemRepository.updateItem(item)
    }

    fun deleteAll() {
        itemRepository.deleteAllItems()
    }

    fun delete(item: Item) {
        itemRepository.delete(item)
    }

    fun getBckgrdCheckEnabled(sharedPreferences: SharedPreferences):Boolean =
        sharedPreferences.getBoolean(BCKGRD_CHECK_ENABLED,false)

    fun setBckgrdCheckEnabled(editor: SharedPreferences.Editor,value:Boolean){
        editor.putBoolean(BCKGRD_CHECK_ENABLED,value)
        editor.apply()
    }



}