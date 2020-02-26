package pl.allegro.follower.view.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import pl.allegro.follower.R
import pl.allegro.follower.model.adapter.ItemAdapter
import pl.allegro.follower.model.data.Item
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
            startActivityForResult(Intent(this, AddItemActivity::class.java),ADD_REQ)

        }

        recyclerView_item_list.setHasFixedSize(true)
        recyclerView_item_list.layoutManager = LinearLayoutManager(this)
        recyclerView_item_list.adapter = adapter
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

        if(requestCode == ADD_REQ){

            if(resultCode == Activity.RESULT_OK){
                val jsonItem = data?.extras?.getString(AddItemActivity.ADD_ID,"")
                val item:Item = Gson().fromJson(jsonItem,Item::class.java)
                itemViewModel.insertItem(item)
                Toast.makeText(this,"Item added",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }

        }



    }


}
