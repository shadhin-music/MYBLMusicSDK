package com.shadhinmusiclibrary.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
@Keep
data class HomePatchDetail(
    val AlbumId: String,
    val AlbumImage: String,
    val AlbumName: String,
    val Artist: String,
    val ArtistId: String,
    val ArtistImage: String,
    val Banner: String,
    var ContentID: String,
    val ContentType: String,
    val CreateDate: String,
    val Duration: String,
    val Follower: String,
    val IsPaid: Boolean,
    val NewBanner: String,
    val PlayCount: Int,
    val PlayListId:String,
    val PlayListImage: String,
    val PlayListName: String,
    val PlayUrl: String,
    var RootId: String,
    val RootType: String,
    val Seekable: Boolean,
    val TeaserUrl: String,
    val TrackType: String,
    val Type: String,
    val fav: String,
    val image: String,
    val imageWeb: String,
    val title: String
) : Serializable, Parcelable {
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }
}