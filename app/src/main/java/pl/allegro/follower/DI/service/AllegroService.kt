package pl.allegro.follower.DI.service

import javax.inject.Inject

class AllegroService @Inject constructor(
    val titlePath: String,
    val pricePath: String,
    val imgPath: String,
    val expiredInPath: String) {
}