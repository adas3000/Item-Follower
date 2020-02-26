package pl.allegro.follower.view.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_item.*
import pl.allegro.follower.R
import pl.allegro.follower.model.data.Item
import pl.allegro.follower.view.viewmodel.AddViewModel

class AddItemActivity : AppCompatActivity() {

    companion object{
        const val ADD_ID = "Item_ADD"
        const val UPDATE_ID = "Item_UPDATE"
        const val TAG = "AddItemActivity"
    }

    private lateinit var viewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_red)
        title = "Add Item"

         viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)

         editText_name.setText(viewModel.itemNameValue.value)
         editText_url.setText(viewModel.itemUrlValue.value)

        editText_name.addTextChangedListener {
            viewModel.setItemName(it.toString())
        }
        editText_url.addTextChangedListener {
            viewModel.setItemUrl(it.toString())
        }


        viewModel.itemValue.observe(this,Observer<Item>{
            val jsonItem :String= Gson().toJson(it)
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(ADD_ID,jsonItem) })
            finish()
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
        progressBar_add_item.visibility = View.VISIBLE

        val itemNameValue = viewModel.itemNameValue.value
        val itemUrlValue = viewModel.itemUrlValue.value

        if(TextUtils.isEmpty(itemUrlValue) || TextUtils.isEmpty(itemNameValue)){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.findItemData(itemUrlValue.toString(),itemNameValue.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onBackPressed() {
        if(progressBar_add_item.isVisible) return
        super.onBackPressed()
    }

}
