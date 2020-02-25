package pl.allegro.follower.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.view.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var itemViewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        itemViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        itemViewModel.liveDataItem.observe(this,Observer<Item>{
            sendNotificationToUser(it)
            updateUI(it)
            //notify user,update ui
        })

        fab_add.setOnClickListener{
            startActivity(Intent(this,
                AddItemActivity::class.java))
        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    private fun sendNotificationToUser(it:Item){

    }

    private fun updateUI(it: Item) {

    }

}
