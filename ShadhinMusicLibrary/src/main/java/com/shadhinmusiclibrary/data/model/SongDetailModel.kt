package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shadhinmusiclibrary.data.IMusicModel


@Keep
internal class SongDetailModel : IMusicModel {
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

    @SerializedName("artist")
    @Expose
    override var artistName: String? = null

    @SerializedName("duration")
    @Expose
    override var total_duration: String? = null

    @SerializedName("copyright")
    @Expose
    var copyright: String? = null

    @SerializedName("labelname")
    @Expose
    var labelname: String? = null

    @SerializedName("releaseDate")
    @Expose
    var releaseDate: String? = null

    @SerializedName("fav")
    @Expose
    var fav: String? = null

    @SerializedName("ArtistId")
    @Expose
    override var artist_Id: String? = null

    override var bannerImage: String? = null
    override var album_Id: String? = null
    override var album_Name: String? = null
    override var rootContentId: String? = null
    override var rootContentType: String? = null
    override var rootImage: String? = null
    override var isPlaying: Boolean = false

    fun getImageUrl300Size(): String? {
        return imageUrl?.replace("<\$size\$>", "300")
    }

//    fun getContentID(): String? {
//        return content_Id
//    }
//
//    fun setContentID(contentID: String?) {
//        this.content_Id = contentID
//    }
//
//    fun getImage(): String? {
//        return imageUrl?.replace("<\$size\$>", "300")
//    }
//
//    fun setImage(image: String) {
//        this.imageUrl = image
//    }
//
//    fun getTitle(): String? {
//        return titleName
//    }
//
//    fun setTitle(title: String) {
//        this.titleName = title
//    }
//
//    fun getContentType(): String? {
//        return content_Type
//    }
//
//    fun setContentType(contentType: String?) {
//        this.content_Type = contentType
//    }
//
//    fun getPlayUrl(): String? {
//        return playingUrl
//    }
//
//    fun setPlayUrl(playUrl: String?) {
//        this.playingUrl = playUrl
//    }
//
//    fun getArtist(): String? {
//        return artistName
//    }
//
//    fun setArtist(artist: String) {
//        this.artistName = artist
//    }
//
//    fun getDuration(): String? {
//        return total_duration
//    }
//
//    fun setDuration(duration: String) {
//        this.total_duration = duration
//    }
//
//    fun getCopyright(): String? {
//        return copyright
//    }
//
//    fun setCopyright(copyright: String) {
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
//    fun getReleaseDate(): String? {
//        return releaseDate
//    }
//
//    fun setReleaseDate(releaseDate: String) {
//        this.releaseDate = releaseDate
//    }
//
//    fun getFav(): String? {
//        return fav
//    }
//
//    fun setFav(fav: String) {
//        this.fav = fav
//    }
//
//    fun getArtistId(): String? {
//        return artist_Id
//    }
//
//    fun setArtistId(artistId: String?) {
//        this.artist_Id = artistId
//    }

//    fun getImageUrl300Size(): String? {
//        return this.imageUrl.replace("<\$size\$>", "300")
//    }

//    fun getRootImageUrl300Size(): String {
//        return this.rootImage?.replace("<\$size\$>", "300")
//    }
}