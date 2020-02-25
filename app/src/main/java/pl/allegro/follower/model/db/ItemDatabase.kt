package pl.allegro.follower.model.db

import androidx.room.Database
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.data.Item


@Database(entities = [Item::class],version = 1)
abstract class ItemDatabase {
    abstract fun itemDao():ItemDao
}