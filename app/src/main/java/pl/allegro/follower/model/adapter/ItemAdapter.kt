package pl.allegro.follower.model.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item

class ItemAdapter:RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var itemList:List<Item> = ArrayList()

    fun setItems(items:List<Item>){
        itemList = items
        notifyDataSetChanged()
    }

    fun getItemAtPosition(i:Int):Item{
        return itemList[i]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false))
    }

    override fun getItemCount(): Int = itemList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemName = itemList[position].itemName
        val itemPrice = itemList[position].itemPrice
        val itemExpiredIn = itemList[position].expiredIn
        val itemLastUpdate = itemList[position].lastUpdate
        val itemLogoURL = itemList[position].itemImgUrl

        Glide.with(holder.img.context)
            .load(itemLogoURL)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.img)

        holder.img.setOnClickListener {
            //to url
        }

        holder.itemName.text = itemName
        holder.itemPrice.text = holder.itemPrice.context.getString(R.string.price_in_pln_text,itemPrice.toString())
        holder.itemLastUpdate.text =itemLastUpdate

        if(itemExpiredIn != null && !TextUtils.isEmpty(itemExpiredIn)){
            holder.itemExpiredIn.visibility = View.VISIBLE
            holder.itemExpiredIn.text = itemExpiredIn
            println(itemExpiredIn)
        }

    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){

        val img = view.findViewById<ImageView>(R.id.imageView_logo)
        val itemName = view.findViewById<TextView>(R.id.textView_item_name)
        val itemPrice = view.findViewById<TextView>(R.id.textView_item_price)
        val itemExpiredIn = view.findViewById<TextView>(R.id.textView_item_expired_in)
        val itemLastUpdate = view.findViewById<TextView>(R.id.textView_item_last_update_time)

    }

}