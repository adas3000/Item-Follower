package pl.allegro.follower.DI.component

import dagger.Component
import pl.allegro.follower.DI.module.AppModule
import pl.allegro.follower.model.repository.ItemRepository
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(itemRepository: ItemRepository)
}