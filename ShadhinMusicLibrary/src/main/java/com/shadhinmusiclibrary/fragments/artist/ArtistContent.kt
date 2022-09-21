package com.shadhinmusiclibrary.fragments.artist

data class ArtistContent(
    val MonthlyListener: String,
    val `data`: List<ArtistContentData>,
    val fav: String,
    val follow: String,
    val image: String,
    val message: String,
    val status: String,
    val total: Int,
    val type: String
){
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }

//    fun getRootImageUrl300Size(): String {
//        return this.rootImage.replace("<\$size\$>", "300")
//    }
}