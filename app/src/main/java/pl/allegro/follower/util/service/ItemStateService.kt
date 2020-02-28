package pl.allegro.follower.util.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import pl.allegro.follower.model.ItemChangeChecker
import pl.allegro.follower.model.repository.ItemRepository

class ItemStateService : Service() {

    companion object {
        const val TAG = "ItemStateService"
    }

    private lateinit var itemChangeChecker: ItemChangeChecker

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        itemChangeChecker = ItemChangeChecker(ItemRepository(application), this)
        itemChangeChecker.findItems()

        return START_NOT_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        itemChangeChecker.clear()
    }

}