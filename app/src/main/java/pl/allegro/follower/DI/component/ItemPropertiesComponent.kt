package pl.allegro.follower.DI.component

import dagger.Component
import pl.allegro.follower.DI.module.AppModule
import pl.allegro.follower.DI.module.ItemPropertiesModule
import pl.allegro.follower.model.dao.ItemDao
import pl.allegro.follower.model.db.ItemDatabase
import pl.allegro.follower.view.viewmodel.MainActivityViewModel
import javax.inject.Singleton

@Component(modules = [ItemPropertiesModule::class])
@Singleton
interface ItemPropertiesComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
}