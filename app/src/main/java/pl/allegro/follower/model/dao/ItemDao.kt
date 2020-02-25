package pl.allegro.follower.model.dao

import androidx.room.*
import io.reactivex.Observable
import pl.allegro.follower.model.data.Item

@Dao
interface ItemDao {

    @Query("select * from item")
    fun getAll():Observable<List<Item>>

    @Insert
    fun insert(item:Item)

    @Update
    fun update(item:Item)

    @Delete
    fun delete(item:Item)

}