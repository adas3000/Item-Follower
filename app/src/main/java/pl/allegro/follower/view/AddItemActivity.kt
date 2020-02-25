package pl.allegro.follower.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import pl.allegro.follower.R

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return true
    }



}
