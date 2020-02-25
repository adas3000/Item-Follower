package pl.allegro.follower.view.viewmodel

import androidx.lifecycle.ViewModel
import pl.allegro.follower.model.repository.AllegroRepository
import javax.inject.Inject
import javax.inject.Named

class MainActivityViewModel : ViewModel {

    val allegroRepository = AllegroRepository()

    constructor(){

    }

    @Inject
    @Named("title")
    lateinit var titlePath: String
    @Inject
    @Named("div")
    lateinit var divPath: String
    @Named("img")
    @Inject
    lateinit var imgPath: String
    @Named("expiredIn")
    @Inject
    lateinit var expiredInPath: String




}