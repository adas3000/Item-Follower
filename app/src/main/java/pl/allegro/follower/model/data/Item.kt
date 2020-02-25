package pl.allegro.follower.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Item(
    @PrimaryKey(autoGenerate = true) val uid: Int, itemName: String, @ColumnInfo(name = "price") var itemPrice: Float, itemURL: String) {


    @ColumnInfo(name = "item_name")
    var itemName:String? = itemName

    @ColumnInfo(name="url")
    var itemURL:String? = itemURL

    @ColumnInfo(name="img")
    var itemImgUrl:String?=""

    @ColumnInfo(name="expiredin")
    var expiredIn:String?=""

    @ColumnInfo(name="lastupdate")
    var lastUpdate:String?=""

}