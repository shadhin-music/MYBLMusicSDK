package com.shadhinmusiclibrary.data.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    @SerializedName("AlbumId")
    var albumId: String? = null,
    @SerializedName("AlbumImage")
    var albumImage: String? = null,
    @SerializedName("AlbumName")
    var albumName: String? = null,
    @SerializedName("Artist")
    var artist: String? = null,
    @SerializedName("ArtistId")
    var artistId: String? = null,
    @SerializedName("ArtistImage")
    var artistImage: String? = null,
    @SerializedName("Banner")
    var banner: String? = null,
    @SerializedName("ClientValue")
    var clientValue: Int? = null,
    @SerializedName("ContentID")
    var contentID: String? = null,
    @SerializedName("ContentType")
    var contentType: String? = null,
    @SerializedName("CreateDate")
    var createDate: String? = null,
    @SerializedName("Duration")
    var duration: String? = null,
    @SerializedName("fav")
    var fav: String? = null,
    @SerializedName("Follower")
    var follower: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("imageWeb")
    var imageWeb: String? = null,
    @SerializedName("IsPaid")
    var isPaid: Boolean? = null,
    @SerializedName("NewBanner")
    var newBanner: String? = null,
    @SerializedName("PlayCount")
    var playCount: Int? = null,
    @SerializedName("PlayListId")
    var playListId: String? = null,
    @SerializedName("PlayListImage")
    var playListImage: String? = null,
    @SerializedName("PlayListName")
    var playListName: String? = null,
    @SerializedName("PlayUrl")
    var playUrl: String? = null,
    @SerializedName("RootId")
    var rootId: String? = null,
    @SerializedName("RootType")
    var rootType: String? = null,
    @SerializedName("Seekable")
    var seekable: Boolean? = null,
    @SerializedName("TeaserUrl")
    var teaserUrl: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("TrackType")
    var trackType: String? = null,
    @SerializedName("Type")
    var type: String? = null,
    var isPlaying: Boolean = false,
    var isPlaystate: Boolean
): Parcelable