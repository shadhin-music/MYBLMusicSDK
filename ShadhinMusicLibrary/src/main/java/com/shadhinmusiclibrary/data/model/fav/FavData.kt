package com.shadhinmusiclibrary.data.model.fav


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Keep
@Entity
data class FavData(

    @SerializedName("ContentID")
    @PrimaryKey(autoGenerate = false)
    var contentID: String,
    @SerializedName("AlbumId")
    var albumId: String?,
    @SerializedName("AlbumImage")
    var albumImage: String?,
    @SerializedName("AlbumName")
    var albumName: String?,
    @SerializedName("Artist")
    var artist: String?,
    @SerializedName("ArtistId")
    var artistId: String?,
    @SerializedName("ArtistImage")
    var artistImage: String?,
    @SerializedName("Banner")
    var banner: String?,
    @SerializedName("ClientValue")
    var clientValue: Int?,
    @SerializedName("ContentType")
    var contentType: String?,
    @SerializedName("CreateDate")
    var createDate: String?,
    @SerializedName("Duration")
    var duration: String?,
    @SerializedName("fav")
    var fav: String?,
    @SerializedName("Follower")
    var follower: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("imageWeb")
    var imageWeb: String?,
    @SerializedName("IsPaid")
    var isPaid: Boolean?,
    @SerializedName("NewBanner")
    var newBanner: String?,
    @SerializedName("PlayCount")
    var playCount: Int?,
    @SerializedName("PlayListId")
    var playListId: String?,
    @SerializedName("PlayListImage")
    var playListImage: String?,
    @SerializedName("PlayListName")
    var playListName:String?,
    @SerializedName("PlayUrl")
    var playUrl: String?,
    @SerializedName("RootId")
    var rootId: String?,
    @SerializedName("RootType")
    var rootType: String?,
    @SerializedName("Seekable")
    var seekable: Boolean?,
    @SerializedName("TeaserUrl")
    var teaserUrl: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("TrackType")
    var trackType: String?,
    @SerializedName("Type")
    var type: String?
): Serializable {
    fun getImageUrl300Size(): String {
        return this.image?.replace("<\$size\$>", "300").toString()
    }
}