package com.shadhinmusiclibrary.data.model.search

import androidx.annotation.Keep
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.shadhinmusiclibrary.data.IMusicModel


@Keep
internal class TopTrendingData : IMusicModel {
    @SerializedName("ContentID")
    @Expose
    override var content_Id: String? = null

    @SerializedName("image")
    @Expose
    override var imageUrl: String? = null

    @SerializedName("title")
    @Expose
    override var titleName: String? = null

    @SerializedName("ContentType")
    @Expose
    override var content_Type: String? = null

    @SerializedName("PlayUrl")
    @Expose
    override var playingUrl: String? = null

    @SerializedName("artistname")
    @Expose
    override var artistName: String? = null

    @SerializedName("duration")
    @Expose
    override var total_duration: String? = null

    @SerializedName("copyright")
    @Expose
    var copyright: Any? = null

    @SerializedName("labelname")
    @Expose
    var labelname: String? = null

    @SerializedName("releaseDate")
    @Expose
    var releaseDate: Any? = null

    @SerializedName("fav")
    @Expose
    var fav: Any? = null

    @SerializedName("AlbumId")
    @Expose
    override var album_Id: String? = null

    @SerializedName("ArtistId")
    @Expose
    override var artist_Id: String? = null

    @SerializedName("TotalStream")
    @Expose
    var totalStream: String? = null

    override var bannerImage: String? = null
    override var album_Name: String? = null
    override var rootContentId: String? = null
    override var rootContentType: String? = null
    override var rootImage: String? = null
    override var isPlaying: Boolean = false

    fun getImageUrl300Size(): String? {
        return imageUrl?.replace("<\$size\$>", "300")
    }

//    fun getContentID(): String? {
//        return contentID
//    }
//
//    fun setContentID(contentID: String?) {
//        this.contentID = contentID
//    }
//
//    fun getImage(): String? {
//        return image
//    }
//
//    fun setImage(image: String) {
//        this.image = image
//    }
//
//    fun getTitle(): String? {
//        return title
//    }
//
//    fun setTitle(title: String) {
//        this.title = title
//    }
//
//    fun getContentType(): String? {
//        return contentType
//    }
//
//    fun setContentType(contentType: String?) {
//        this.contentType = contentType
//    }
//
//    fun getPlayUrl(): String? {
//        return playUrl
//    }
//
//    fun setPlayUrl(playUrl: String?) {
//        this.playUrl = playUrl
//    }
//
//    fun getArtistname(): String? {
//        return artistname
//    }
//
//    fun setArtistname(artistname: String) {
//        this.artistname = artistname
//    }
//
//    fun getDuration(): String? {
//        return duration
//    }
//
//    fun setDuration(duration: String) {
//        this.duration = duration
//    }
//
//    fun getCopyright(): Any? {
//        return copyright
//    }
//
//    fun setCopyright(copyright: Any) {
//        this.copyright = copyright
//    }
//
//    fun getLabelname(): String? {
//        return labelname
//    }
//
//    fun setLabelname(labelname: String) {
//        this.labelname = labelname
//    }
//
//    fun getReleaseDate(): Any? {
//        return releaseDate
//    }
//
//    fun setReleaseDate(releaseDate: Any) {
//        this.releaseDate = releaseDate
//    }
//
//    fun getFav(): Any? {
//        return fav
//    }
//
//    fun setFav(fav: Any) {
//        this.fav = fav
//    }
//
//    fun getAlbumId(): String? {
//        return albumId
//    }
//
//    fun setAlbumId(albumId: String?) {
//        this.albumId = albumId
//    }
//
//    fun getArtistId(): String? {
//        return artistId
//    }
//
//    fun setArtistId(artistId: String?) {
//        this.artistId = artistId
//    }
//
//    fun getTotalStream(): String? {
//        return totalStream
//    }
//
//    fun setTotalStream(totalStream: String?) {
//        this.totalStream = totalStream
//    }
}