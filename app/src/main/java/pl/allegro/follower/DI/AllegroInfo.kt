package pl.allegro.follower.DI

import javax.inject.Inject

class AllegroInfo @Inject constructor(
    val titlePath: String,
    val pricePath: String,
    val imgPath: String,
    val expiredInPath: String) {
}