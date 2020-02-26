package pl.allegro.follower.model.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item

class ItemAdapter:RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var itemList:List<Item> = ArrayList()

    fun setItems(items:List<Item>){
        itemList = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false))
    }

    override fun getItemCount(): Int = itemList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.img.context)
            .load(itemList[position].itemImgUrl)
            .into(holder.img)

        holder.img.setOnClickListener {
            //to url
        }
        holder.itemName.text = itemList[position].itemName+"\n"+itemList[position].itemPrice
        holder.itemExpiredIn.text = itemList[position].expiredIn
        holder.itemLastUpdate.text = itemList[position].lastUpdate

    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){

        val img = view.findViewById<ImageView>(R.id.imageView_logo)
        val itemName = view.findViewById<TextView>(R.id.textView_item_name)
        val itemExpiredIn = view.findViewById<TextView>(R.id.textView_item_expired_in)
        val itemLastUpdate = view.findViewById<TextView>(R.id.textView_item_last_update_time)

    }

}