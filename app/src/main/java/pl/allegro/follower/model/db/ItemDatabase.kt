package pl.allegro.follower.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.data.Item


@Database(entities = [Item::class],version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao():ItemDao
}