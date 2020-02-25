package pl.allegro.follower.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import pl.allegro.follower.DI.component.DaggerAppComponent
import pl.allegro.follower.DI.module.AppModule
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.data.Item
import javax.inject.Inject

class ItemRepository(application: Application) {

    @Inject
    lateinit var itemDao: ItemDao

    private var allItems:LiveData<List<Item>>

    init {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)

        allItems = itemDao.getAll()
    }

    fun getAllItems():LiveData<List<Item>>{
        return allItems
    }


    suspend fun updateItem(item: Item){
        itemDao.update(item)
    }

    suspend fun insertItem(item:Item){
        itemDao.insert(item)
    }

    suspend fun deleteAllItems(){
        itemDao.deleteAll()
    }

    suspend fun delete(item:Item){
        itemDao.delete(item)
    }

}