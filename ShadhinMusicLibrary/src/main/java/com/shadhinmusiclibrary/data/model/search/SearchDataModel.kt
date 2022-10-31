package com.shadhinmusiclibrary.data.model.search

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shadhinmusiclibrary.data.IMusicModel


@Keep
internal class SearchDataModel : IMusicModel {
    @SerializedName("ContentID")
    override var content_Id: String? = null

    @SerializedName("image")
    override var imageUrl: String? = null

    @SerializedName("imageWeb")
    var imageWeb: String? = null

    @SerializedName("title")
    override var titleName: String? = null

    @SerializedName("ContentType")
    override var content_Type: String? = null

    @SerializedName("PlayUrl")
    override var playingUrl: String? = null

    @SerializedName("Duration")
    override var total_duration: String? = null

    @SerializedName("fav")
    var fav: String? = null

    @SerializedName("Banner")
    override var bannerImage: String? = null

    @SerializedName("NewBanner")
    var newBanner: String? = null

    @SerializedName("PlayCount")
    var playCount: Int? = null

    @SerializedName("Type")
    var type: String? = null

    @SerializedName("IsPaid")
    var isPaid: Boolean? = null

    @SerializedName("Seekable")
    var seekable: Boolean? = null

    @SerializedName("TrackType")
    var trackType: String? = null

    @SerializedName("ArtistId")
    override var artist_Id: String? = null

    @SerializedName("Artist")
    override var artistName: String? = null

    @SerializedName("ArtistImage")
    var artistImage: String? = null

    @SerializedName("AlbumId")
    override var album_Id: String? = null

    @SerializedName("AlbumName")
    override var album_Name: String? = null

    @SerializedName("AlbumImage")
    var albumImage: String? = null

    @SerializedName("PlayListId")
    var playListId: String? = null

    @SerializedName("PlayListName")
    var playListName: String? = null

    @SerializedName("PlayListImage")
    var playListImage: String? = null

    @SerializedName("CreateDate")
    var createDate: String? = null

    @SerializedName("RootId")
    override var rootContentId: String? = null

    @SerializedName("RootType")
    override var rootContentType: String? = null

    @SerializedName("TeaserUrl")
    var teaserUrl: String? = null

    @SerializedName("Follower")
    var follower: String? = null

    @SerializedName("ClientValue")
    var clientValue: Int? = null

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
//    fun getImageWeb(): Any? {
//        return imageWeb
//    }
//
//    fun setImageWeb(imageWeb: Any) {
//        this.imageWeb = imageWeb
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
//    fun getDuration(): String? {
//        return total_duration
//    }
//
//    fun setDuration(duration: String?) {
//        this.total_duration = duration
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
//    fun getBanner(): String? {
//        return bannerImage
//    }
//
//    fun setBanner(banner: String?) {
//        this.bannerImage = banner
//    }
//
//    fun getNewBanner(): Any? {
//        return newBanner
//    }
//
//    fun setNewBanner(newBanner: Any?) {
//        this.newBanner = newBanner
//    }
//
//    fun getPlayCount(): Int? {
//        return playCount
//    }
//
//    fun setPlayCount(playCount: Int?) {
//        this.playCount = playCount
//    }
//
//    fun getType(): String? {
//        return type
//    }
//
//    fun setType(type: String?) {
//        this.type = type
//    }
//
//    fun getIsPaid(): Boolean? {
//        return isPaid
//    }
//
//    fun setIsPaid(isPaid: Boolean?) {
//        this.isPaid = isPaid
//    }
//
//    fun getSeekable(): Boolean? {
//        return seekable
//    }
//
//    fun setSeekable(seekable: Boolean?) {
//        this.seekable = seekable
//    }
//
//    fun getTrackType(): String? {
//        return trackType
//    }
//
//    fun setTrackType(trackType: String?) {
//        this.trackType = trackType
//    }
//
//    fun getArtistId(): String? {
//        return artist_Id
//    }
//
//    fun setArtistId(artistId: String?) {
//        this.artist_Id = artistId
//    }
//
//    fun getArtist(): String? {
//        return artistName
//    }
//
//    fun setArtist(artist: String?) {
//        this.artistName = artist
//    }
//
//    fun getArtistImage(): Any? {
//        return artistImage
//    }
//
//    fun setArtistImage(artistImage: Any?) {
//        this.artistImage = artistImage
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
//    fun getAlbumName(): String? {
//        return album_Name
//    }
//
//    fun setAlbumName(albumName: String?) {
//        this.album_Name = albumName
//    }
//
//    fun getAlbumImage(): Any? {
//        return albumImage
//    }
//
//    fun setAlbumImage(albumImage: Any?) {
//        this.albumImage = albumImage
//    }
//
//    fun getPlayListId(): Any? {
//        return playListId
//    }
//
//    fun setPlayListId(playListId: Any?) {
//        this.playListId = playListId
//    }
//
//    fun getPlayListName(): Any? {
//        return playListName
//    }
//
//    fun setPlayListName(playListName: Any?) {
//        this.playListName = playListName
//    }
//
//    fun getPlayListImage(): Any? {
//        return playListImage
//    }
//
//    fun setPlayListImage(playListImage: Any?) {
//        this.playListImage = playListImage
//    }
//
//    fun getCreateDate(): Any? {
//        return createDate
//    }
//
//    fun setCreateDate(createDate: Any?) {
//        this.createDate = createDate
//    }
//
//    fun getRootId(): String? {
//        return rootContentId
//    }
//
//    fun setRootId(rootId: String?) {
//        this.rootContentId = rootId
//    }
//
//    fun getRootType(): String? {
//        return rootContentType
//    }
//
//    fun setRootType(rootType: String?) {
//        this.rootContentType = rootType
//    }
//
//    fun getTeaserUrl(): Any? {
//        return teaserUrl
//    }
//
//    fun setTeaserUrl(teaserUrl: Any?) {
//        this.teaserUrl = teaserUrl
//    }
//
//    fun getFollower(): Any? {
//        return follower
//    }
//
//    fun setFollower(follower: Any?) {
//        this.follower = follower
//    }
//
//    fun getClientValue(): Int? {
//        return clientValue
//    }
//
//    fun setClientValue(clientValue: Int?) {
//        this.clientValue = clientValue
//    }
}