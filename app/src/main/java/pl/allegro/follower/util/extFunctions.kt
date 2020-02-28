package pl.allegro.follower.util

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

fun String.strPriceToFloat():Float{
    var newStr :String= this.replace(" ","")
    newStr = newStr.replace(",",".")
    newStr = newStr.replace("[^0-9.]".toRegex(),"")
    return newStr.toFloat()
}
fun TextView.setExpiredIn(expiredIn: String?) {

    if (expiredIn != null && !TextUtils.isEmpty(expiredIn)) {
        this.visibility = View.VISIBLE
        this.text = expiredIn
    }

}

fun ImageView.loadImageFromUrl(url: String?) {
    if (url != null) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

fun ImageView.onClickRedirectToUrl(url:String?){
    if(url!=null) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}