package pl.allegro.follower.model.repository

import javax.inject.Inject
import javax.inject.Named

class AllegroRepository @Inject constructor(
    @Named("title") val titlePath: String, @Named("div") val divPath: String,
    @Named("img") val imgPath: String, @Named("expiredIn")val expiredInPath: String
) {




}