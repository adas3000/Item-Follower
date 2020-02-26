package pl.allegro.follower.view.ui

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import pl.allegro.follower.R
import pl.allegro.follower.model.adapter.ItemAdapter
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.util.broadcast.CheckItemsStateBroadcastReceiver
import pl.allegro.follower.view.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_REQ = 7
    }


    private lateinit var itemViewModel: MainActivityViewModel
    private val adapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, CheckItemsStateBroadcastReceiver::class.java)
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            5000,
            PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        )

        itemViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        itemViewModel.liveDataItem.observe(this, Observer<Item> {
            sendNotificationToUser(it)
            updateUI(it)
            //notify user,update ui
        })

        itemViewModel.itemListLiveData.observe(this, Observer<List<Item>> {
            adapter.setItems(it)
        })

        fab_add.setOnClickListener {
            startActivityForResult(Intent(this, AddItemActivity::class.java), ADD_REQ)
        }



        recyclerView_item_list.setHasFixedSize(true)
        recyclerView_item_list.layoutManager = LinearLayoutManager(this)
        recyclerView_item_list.adapter = adapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                itemViewModel.delete(adapter.getItemAtPosition(viewHolder.adapterPosition))
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.item_deleted_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }).attachToRecyclerView(recyclerView_item_list)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun sendNotificationToUser(it: Item) {

    }

    private fun updateUI(it: Item) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REQ) {

            if (resultCode == Activity.RESULT_OK) {
                val jsonItem = data?.extras?.getString(AddItemActivity.ADD_ID, "")
                val item: Item = Gson().fromJson(jsonItem, Item::class.java)
                itemViewModel.insertItem(item)

                Toast.makeText(this, getString(R.string.item_added_text), Toast.LENGTH_SHORT).show()
            }

        }


    }


}
