package pl.allegro.follower.DI.component

import dagger.Component
import pl.allegro.follower.DI.module.ItemPropertiesModule
import pl.allegro.follower.model.ItemChangeChecker
import pl.allegro.follower.view.viewmodel.AddViewModel
import javax.inject.Singleton

@Component(modules = [ItemPropertiesModule::class])
@Singleton
interface ItemPropertiesComponent {
    fun inject(addViewModel: AddViewModel)
    fun inject(itemChangeChecker: ItemChangeChecker)
}