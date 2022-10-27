package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shadhinmusiclibrary.data.IMusicModel

@Keep
internal class SongTrack : IMusicModel {
    override var content_Id: String?
        get() = id.toString()
        set(value) {}

    @SerializedName("Id")
    var id: Int? = null

    @SerializedName("ShowId")
    @Expose
    var showId: String? = null

    @SerializedName("EpisodeId")
    @Expose
    var episodeId: String? = null

    @SerializedName("ImageUrl")
    @Expose
    override var imageUrl: String? = null

    @SerializedName("Name")
    @Expose
    override var titleName: String? = null

    override var bannerImage: String? = null

    @SerializedName("ContentType")
    @Expose
    override var content_Type: String? = null

    @SerializedName("PlayUrl")
    @Expose
    override var playingUrl: String? = null

/*    @SerializedName("artistname")
    @Expose*/
//    var artistname: String? = null

    @SerializedName("Duration")
    @Expose
    override var total_duration: String? = null

//    @SerializedName("copyright")
//    @Expose
//    var copyright: String? = null

//    @SerializedName("labelname")
//    @Expose
//    var labelname: String? = null

    //    @SerializedName("releaseDate")
//    @Expose
    var releaseDate: String? = null

    @SerializedName("fav")
    @Expose
    var fav: String? = null

    //    @SerializedName("albumId")
//    @Expose
    override var album_Id: String? = null

    //    @SerializedName("artistId")
//    @Expose
    override var artist_Id: String? = null

    @SerializedName("Starring")
    @Expose
    var starring: String? = null

    @SerializedName("Seekable")
    @Expose
    var seekable: Boolean? = null

    @SerializedName("Details")
    @Expose
    var details: String? = null

    @SerializedName("CeateDate")
    @Expose
    var ceateDate: String? = null

    @SerializedName("totalStream")
    @Expose
    var totalStream: Int? = null

    @SerializedName("Sort")
    @Expose
    var sort: Int? = null

    @SerializedName("TrackType")
    @Expose
    var trackType: String? = null

    @SerializedName("IsPaid")
    @Expose
    var isPaid: Boolean? = null

    override var album_Name: String? = null
    override var artistName: String? = null
    override var rootContentId: String? = null
    override var rootContentType: String? = null
    override var rootImage: String? = null
    override var isPlaying: Boolean = false

    fun getImageUrl300Size(): String? {
        return imageUrl?.replace("<\$size\$>", "300")
    }

//    fun getContentId(): String? {
//        return content_Id
//    }
//
//    fun setContentId(contentID: String?) {
//        this.content_Id = contentID
//    }
//
//    fun getImage(): String? {
//        return imageUrl?.replace("<\$size\$>", "300")
//    }
//
//    fun setImage(image: String?) {
//        this.imageUrl = image
//    }
//
//    fun getTitle(): String? {
//        return titleName
//    }
//
//    fun setTitle(title: String?) {
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
//    fun getArtistname(): String? {
//        return artistname
//    }
//
//    fun setArtistname(artistname: String?) {
//        this.artistname = artistname
//    }
//
//    fun getDuration(): String? {
//        return total_duration
//    }
//
//    fun setDuration(duration: String?) {
//        this.total_duration = duration
//    }
//
//    fun getCopyright(): String? {
//        return copyright
//    }
//
//    fun setCopyright(copyright: String?) {
//        this.copyright = copyright
//    }
//
//    fun getLabelname(): String? {
//        return labelname
//    }
//
//    fun setLabelname(labelname: String?) {
//        this.labelname = labelname
//    }
//
//    fun getReleaseDate(): String? {
//        return releaseDate
//    }
//
//    fun setReleaseDate(releaseDate: String?) {
//        this.releaseDate = releaseDate
//    }
//
//    fun getFav(): Any? {
//        return fav
//    }
//
//    fun setFav(fav: Any?) {
//        this.fav = fav
//    }
//
//    fun getAlbumId(): String? {
//        return album_Id
//    }
//
//    fun setAlbumId(albumId: String?) {
//        this.album_Id = albumId
//    }
//
//    fun getArtistId(): String? {
//        return artist_Id
//    }
//
//    fun setArtistId(artistId: String?) {
//        this.artist_Id = artistId
//    }
}