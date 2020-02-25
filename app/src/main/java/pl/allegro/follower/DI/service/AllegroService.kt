package pl.allegro.follower.DI.service

import javax.inject.Inject

class AllegroService {

    val titlePath: String
    val pricePath: String
    val imgPath: String
    val expiredInPath: String

    @Inject
    constructor(titlePath: String,pricePath: String, imgPath: String,expiredInPath: String) {
        this.titlePath = titlePath
        this.pricePath = pricePath
        this.imgPath = imgPath
        this.expiredInPath = expiredInPath
    }



}