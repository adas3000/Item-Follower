package pl.allegro.follower.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import pl.allegro.follower.R

class AddItemActivity : AppCompatActivity() {

    companion object{
        const val ADD_ID = "Added_Item_Data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menu_action_save -> {



                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun addItem(){

    }



}
