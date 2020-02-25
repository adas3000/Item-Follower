package pl.allegro.follower.component

import dagger.Component
import pl.allegro.follower.model.repository.AllegroRepository
import pl.allegro.follower.module.dagger.ItemPropertiesModule

@Component(modules = [ItemPropertiesModule::class])
interface ItemPropertiesComponent {
    fun inject(allegroRepository: AllegroRepository)
}