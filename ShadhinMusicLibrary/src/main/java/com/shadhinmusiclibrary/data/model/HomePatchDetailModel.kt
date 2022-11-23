package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shadhinmusiclibrary.data.IMusicModel

@Keep
internal class HomePatchDetailModel : IMusicModel {
    @SerializedName("ContentID")
    @Expose
    override var content_Id: String = ""

    @SerializedName("image")
    @Expose
    override var imageUrl: String? = null

    @SerializedName("imageWeb")
    @Expose
    var imageWeb: Any? = null

    @SerializedName("title")
    @Expose
    override var titleName: String? = null

    @SerializedName("ContentType")
    @Expose
    override var content_Type: String? = null

    @SerializedName("PlayUrl")
    @Expose
    override var playingUrl: String? = null

    @SerializedName("Duration")
    @Expose
    override var total_duration: String? = null

    @SerializedName("fav")
    @Expose
    private var fav: String? = null

    @SerializedName("Banner")
    @Expose
    override var bannerImage: String? = null

    @SerializedName("NewBanner")
    @Expose
    private var newBanner: String? = null

    @SerializedName("PlayCount")
    @Expose
    private var playCount: Int? = null

    @SerializedName("Type")
    @Expose
    private var type: String? = null

    @SerializedName("IsPaid")
    @Expose
    private var isPaid: Boolean? = null

    @SerializedName("Seekable")
    @Expose
    override var isSeekAble: Boolean? = null

    @SerializedName("TrackType")
    @Expose
    private var trackType: String? = null

    @SerializedName("ArtistId")
    @Expose
    override var artist_Id: String? = null

    @SerializedName("Artist")
    @Expose
    override var artistName: String? = null

    @SerializedName("ArtistImage")
    @Expose
    private var artistImage: Any? = null

    @SerializedName("AlbumId")
    @Expose
    override var album_Id: String? = null

    @SerializedName("AlbumName")
    @Expose
    override var album_Name: String? = null

    @SerializedName("AlbumImage")
    @Expose
    var albumImage: Any? = null

    @SerializedName("PlayListId")
    @Expose
    var playListId: Any? = null

    @SerializedName("PlayListName")
    @Expose
    var playListName: Any? = null

    @SerializedName("PlayListImage")
    @Expose
    var playListImage: Any? = null

    @SerializedName("CreateDate")
    @Expose
    val createDate: Any? = null

    @SerializedName("RootId")
    @Expose
    override var rootContentId: String? = null

    @SerializedName("RootType")
    @Expose
    override var rootContentType: String? = null

    @SerializedName("TeaserUrl")
    @Expose
    var teaserUrl: String? = null

    @SerializedName("Follower")
    @Expose
    var follower: Any? = null

    @SerializedName("ClientValue")
    @Expose
    var clientValue: Int? = null

    override var rootImage: String? = null

    override var isPlaying: Boolean = false
}