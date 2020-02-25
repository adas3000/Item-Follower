package pl.allegro.follower.DI.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.db.ItemDatabase
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    private val dbName = "itemFollowerDB01"
    private val databaseInstance: ItemDatabase

    init {
        databaseInstance = Room
            .databaseBuilder(context, ItemDatabase::class.java, dbName)
            .build()
    }


    @Provides
    @Singleton
    fun provideDatabaseInstance(): ItemDatabase {
        return databaseInstance
    }

    @Provides
    @Singleton
    fun provideItemsDao():ItemDao{
        return databaseInstance.itemDao()
    }

}