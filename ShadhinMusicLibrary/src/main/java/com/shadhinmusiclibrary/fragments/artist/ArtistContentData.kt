package com.shadhinmusiclibrary.fragments.artist

data class ArtistContentData(
    val AlbumId: String,
    val ArtistId: String,
    val ContentID: String,
    val ContentType: String,
    val PlayUrl: String,
    val TotalPlay: Int,
    val artistname: String,
    val copyright: String,
    val duration: String,
    val fav: String,
    val image: String,
    val labelname: String,
    val releaseDate: String,
    val title: String
)