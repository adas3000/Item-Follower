package pl.allegro.follower.model.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.util.loadImageFromUrl
import pl.allegro.follower.util.setExpiredIn
import pl.allegro.follower.util.onClickRedirectToUrl

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var itemList: List<Item> = ArrayList()

    fun setItems(items: List<Item>) {
        itemList = items
        notifyDataSetChanged()
    }

    fun getItemAtPosition(i: Int): Item {
        return itemList[i]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = itemList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemName = itemList[position].itemName
        val itemPrice = itemList[position].itemPrice
        val itemExpiredIn = itemList[position].expiredIn
        val itemLastUpdate = itemList[position].lastUpdate
        val itemLogoURL = itemList[position].itemImgUrl

        holder.img.loadImageFromUrl(itemLogoURL)
        holder.img.setOnClickListener { holder.img.onClickRedirectToUrl(itemList[position].itemURL) }
        holder.itemName.text = itemName
        holder.itemPrice.text = holder.itemPrice.context.getString(R.string.price_in_pln_text, itemPrice.toString())
        holder.itemLastUpdate.text = itemLastUpdate
        holder.itemExpiredIn.setExpiredIn(itemExpiredIn)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val img: ImageView = view.findViewById(R.id.imageView_logo)
        val itemName: TextView = view.findViewById(R.id.textView_item_name)
        val itemPrice: TextView = view.findViewById(R.id.textView_item_price)
        val itemExpiredIn: TextView = view.findViewById(R.id.textView_item_expired_in)
        val itemLastUpdate: TextView = view.findViewById(R.id.textView_item_last_update_time)

    }
}

