package pl.allegro.follower.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_item.*
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.view.viewmodel.AddViewModel

class AddItemActivity : AppCompatActivity() {

    companion object{
        const val ADD_ID = "Added_Item_Data"
        const val TAG = "AddItemActivity"
    }

    private lateinit var viewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

         viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)

         editText_name.setText(viewModel.itemNameValue.value)
         editText_url.setText(viewModel.itemUrlValue.value)

        viewModel.itemValue.observe(this,Observer<Item>{

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menu_action_save -> {
                addItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun addItem(){

        val itemNameValue = viewModel.itemNameValue.value
        val itemUrlValue = viewModel.itemUrlValue.value

        if(TextUtils.isEmpty(itemNameValue) || TextUtils.isEmpty(itemUrlValue)){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.findItemData(itemUrlValue.toString(),itemNameValue.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }


}
