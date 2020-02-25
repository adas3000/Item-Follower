package pl.allegro.follower.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.allegro.follower.model.data.Item

@Dao
interface ItemDao {

    @Query("select * from item")
    fun getAll():LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:Item)

    @Update
    suspend fun update(item:Item)

    @Delete
    suspend fun delete(item:Item)

    @Query("delete from item")
    suspend fun deleteAll()
}