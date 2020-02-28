package pl.allegro.follower.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jsoup.nodes.Document
import pl.allegro.follower.DI.AllegroInfo
import pl.allegro.follower.util.strPriceToFloat
import java.text.SimpleDateFormat
import java.util.*

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

//    fun findDataInAllegroDocument(userInputName:String,allegroInfo: AllegroInfo,doc: Document){
//        val title: String =
//            if (userInputName.isNotEmpty()) userInputName
//            else
//                doc.selectFirst(allegroInfo.titlePath).text()
//
//        val strPrice: String = doc.selectFirst(allegroInfo.pricePath).text()
//        val imgUrl: String = doc.selectFirst(allegroInfo.imgPath).absUrl("src")
//
//        var expiredIn: String? = ""
//        if (doc.selectFirst(allegroInfo.expiredInPath) != null)
//            expiredIn = doc.selectFirst(allegroInfo.expiredInPath).text()
//        val dateFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US)
//
//        this.itemName = title
//        this.itemPrice = strPrice.strPriceToFloat()
//        this.itemImgUrl = imgUrl
//        this.expiredIn = expiredIn
//        this.lastUpdate = dateFormatter.format(Date())
//    }

}