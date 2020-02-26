package pl.allegro.follower.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.allegro.follower.DI.component.DaggerAppComponent
import pl.allegro.follower.DI.module.AppModule
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.data.Item
import javax.inject.Inject

class ItemRepository(application: Application) {

    @Inject
    lateinit var itemDao: ItemDao

    private var allItems: LiveData<List<Item>>

    init {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)

        allItems = itemDao.getAll()
    }

    fun getAllItems(): LiveData<List<Item>> {
        return allItems
    }

    fun getObservableAllItems():Observable<List<Item>>{
        return itemDao.getAllObservable()
    }

    fun updateItem(item: Item) {
        GlobalScope.launch {
            itemDao.update(item)
        }
    }

    fun insertItem(item: Item) {
        GlobalScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteAllItems() {
        GlobalScope.launch {
            itemDao.deleteAll()
        }
    }

    fun delete(item: Item) {
        GlobalScope.launch {
            itemDao.delete(item)
        }
    }

}