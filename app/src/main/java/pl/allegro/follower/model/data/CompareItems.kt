package pl.allegro.follower.model.data

import org.jsoup.nodes.Document

data class CompareItems(
    val scrapDocument:Document,
    val compareItem:Item
)