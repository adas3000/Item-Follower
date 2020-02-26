package pl.allegro.follower.util.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CheckItemsStateBroadcastReceiver : BroadcastReceiver(){


    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"ALARM!!",Toast.LENGTH_SHORT).show()
    }

}