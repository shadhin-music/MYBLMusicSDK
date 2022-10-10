package com.shadhinmusiclibrary.data.model.search

data class TopTrendingdata(
    val AlbumId: String,
    val ArtistId: String,
    val ContentID: String,
    val ContentType: String,
    val PlayUrl: String,
    val TotalStream: String,
    val artistname: String,
    val copyright: Any,
    val duration: String,
    val fav: Any,
    val image: String,
    val labelname: String,
    val releaseDate: Any,
    val title: String
){
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }
}