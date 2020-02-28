package pl.allegro.follower.view.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pl.allegro.follower.model.ItemChangeChecker
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.model.repository.ItemRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "MainActivityViewModel"
        const val BCKGRD_CHECK_ENABLED =  "background_check"
    }

    val itemListLiveData: LiveData<List<Item>>

    private val itemRepository: ItemRepository = ItemRepository(application)
    private val itemChangeChecker:ItemChangeChecker = ItemChangeChecker(itemRepository,application)

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

    fun refreshItemsState(){
        itemChangeChecker.findItems()
    }

    fun clear(){
        itemChangeChecker.clear()
    }

}