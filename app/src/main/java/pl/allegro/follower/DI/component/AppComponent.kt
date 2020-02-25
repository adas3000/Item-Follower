package pl.allegro.follower.DI.component

import dagger.Component
import pl.allegro.follower.DI.module.AppModule
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.db.ItemDatabase
import pl.allegro.follower.view.ui.AddItemActivity
import pl.allegro.follower.view.ui.MainActivity

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(addItemActivity: AddItemActivity)

    fun itemDatabase():ItemDatabase

    fun itemDao():ItemDao
}