package pl.allegro.follower.component

import dagger.Component
import pl.allegro.follower.module.dagger.ItemPropertiesModule
import pl.allegro.follower.view.MainActivity

@Component(modules = [ItemPropertiesModule::class])
interface ItemPropertiesComponent {
    fun inject(mainActivity: MainActivity)
}