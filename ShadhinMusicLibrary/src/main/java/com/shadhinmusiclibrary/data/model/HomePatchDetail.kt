package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
internal class HomePatchDetail {
    @SerializedName("ContentID")
    @Expose
    private var contentID: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("imageWeb")
    @Expose
    private var imageWeb: Any? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("ContentType")
    @Expose
    private var contentType: String? = null

    @SerializedName("PlayUrl")
    @Expose
    private var playUrl: String? = null

    @SerializedName("Duration")
    @Expose
    private var duration: String? = null

    @SerializedName("fav")
    @Expose
    private var fav: String? = null

    @SerializedName("Banner")
    @Expose
    private var banner: String? = null

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
    private var seekable: Boolean? = null

    @SerializedName("TrackType")
    @Expose
    private var trackType: String? = null

    @SerializedName("ArtistId")
    @Expose
    private var artistId: String? = null

    @SerializedName("Artist")
    @Expose
    private var artist: String? = null

    @SerializedName("ArtistImage")
    @Expose
    private var artistImage: Any? = null

    @SerializedName("AlbumId")
    @Expose
    private var albumId: String? = null

    @SerializedName("AlbumName")
    @Expose
    private var albumName: Any? = null

    @SerializedName("AlbumImage")
    @Expose
    private var albumImage: Any? = null

    @SerializedName("PlayListId")
    @Expose
    private var playListId: Any? = null

    @SerializedName("PlayListName")
    @Expose
    private var playListName: Any? = null

    @SerializedName("PlayListImage")
    @Expose
    private var playListImage: Any? = null

    @SerializedName("CreateDate")
    @Expose
    private var createDate: Any? = null

    @SerializedName("RootId")
    @Expose
    private var rootId: Any? = null

    @SerializedName("RootType")
    @Expose
    private var rootType: String? = null

    @SerializedName("TeaserUrl")
    @Expose
    private var teaserUrl: String? = null

    @SerializedName("Follower")
    @Expose
    private var follower: Any? = null

    @SerializedName("ClientValue")
    @Expose
    private var clientValue: Int? = null

    constructor(
        contentID: String?,
        image: String?,
        imageWeb: Any?,
        title: String?,
        contentType: String?,
        playUrl: String?,
        duration: String?,
        fav: String?,
        banner: String?,
        newBanner: String?,
        playCount: Int?,
        type: String?,
        isPaid: Boolean?,
        seekable: Boolean?,
        trackType: String?,
        artistId: String?,
        artist: String?,
        artistImage: Any?,
        albumId: String?,
        albumName: Any?,
        albumImage: Any?,
        playListId: Any?,
        playListName: Any?,
        playListImage: Any?,
        createDate: Any?,
        rootId: Any?,
        rootType: String?,
        teaserUrl: String?,
        follower: Any?,
        clientValue: Int?
    ) {
        this.contentID = contentID
        this.image = image
        this.imageWeb = imageWeb
        this.title = title
        this.contentType = contentType
        this.playUrl = playUrl
        this.duration = duration
        this.fav = fav
        this.banner = banner
        this.newBanner = newBanner
        this.playCount = playCount
        this.type = type
        this.isPaid = isPaid
        this.seekable = seekable
        this.trackType = trackType
        this.artistId = artistId
        this.artist = artist
        this.artistImage = artistImage
        this.albumId = albumId
        this.albumName = albumName
        this.albumImage = albumImage
        this.playListId = playListId
        this.playListName = playListName
        this.playListImage = playListImage
        this.createDate = createDate
        this.rootId = rootId
        this.rootType = rootType
        this.teaserUrl = teaserUrl
        this.follower = follower
        this.clientValue = clientValue
    }


    fun getContentID(): String? {
        return contentID
    }

    fun setContentID(contentID: String) {
        this.contentID = contentID
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String) {
        this.image = image
    }

    fun getImageWeb(): Any? {
        return imageWeb
    }

    fun setImageWeb(imageWeb: Any) {
        this.imageWeb = imageWeb
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getContentType(): String? {
        return contentType
    }

    fun setContentType(contentType: String) {
        this.contentType = contentType
    }

    fun getPlayUrl(): String? {
        return playUrl
    }

    fun setPlayUrl(playUrl: String) {
        this.playUrl = playUrl
    }

    fun getDuration(): String? {
        return duration
    }

    fun setDuration(duration: String) {
        this.duration = duration
    }

    fun getFav(): String? {
        return fav
    }

    fun setFav(fav: String) {
        this.fav = fav
    }

    fun getBanner(): String? {
        return banner
    }

    fun setBanner(banner: String) {
        this.banner = banner
    }

    fun getNewBanner(): String? {
        return newBanner
    }

    fun setNewBanner(newBanner: String) {
        this.newBanner = newBanner
    }

    fun getPlayCount(): Int? {
        return playCount
    }

    fun setPlayCount(playCount: Int) {
        this.playCount = playCount
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getIsPaid(): Boolean? {
        return isPaid
    }

    fun setIsPaid(isPaid: Boolean) {
        this.isPaid = isPaid
    }

    fun getSeekable(): Boolean? {
        return seekable
    }

    fun setSeekable(seekable: Boolean) {
        this.seekable = seekable
    }

    fun getTrackType(): String? {
        return trackType
    }

    fun setTrackType(trackType: String) {
        this.trackType = trackType
    }

    fun getArtistId(): String? {
        return artistId
    }

    fun setArtistId(artistId: String) {
        this.artistId = artistId
    }

    fun getArtist(): String? {
        return artist
    }

    fun setArtist(artist: String) {
        this.artist = artist
    }

    fun getArtistImage(): Any? {
        return artistImage
    }

    fun setArtistImage(artistImage: Any) {
        this.artistImage = artistImage
    }

    fun getAlbumId(): String? {
        return albumId
    }

    fun setAlbumId(albumId: String) {
        this.albumId = albumId
    }

    fun getAlbumName(): Any? {
        return albumName
    }

    fun setAlbumName(albumName: Any) {
        this.albumName = albumName
    }

    fun getAlbumImage(): Any? {
        return albumImage
    }

    fun setAlbumImage(albumImage: Any) {
        this.albumImage = albumImage
    }

    fun getPlayListId(): Any? {
        return playListId
    }

    fun setPlayListId(playListId: Any) {
        this.playListId = playListId
    }

    fun getPlayListName(): Any? {
        return playListName
    }

    fun setPlayListName(playListName: Any) {
        this.playListName = playListName
    }

    fun getPlayListImage(): Any? {
        return playListImage
    }

    fun setPlayListImage(playListImage: Any) {
        this.playListImage = playListImage
    }

    fun getCreateDate(): Any? {
        return createDate
    }

    fun setCreateDate(createDate: Any) {
        this.createDate = createDate
    }

    fun getRootId(): Any? {
        return rootId
    }

    fun setRootId(rootId: Any) {
        this.rootId = rootId
    }

    fun getRootType(): String? {
        return rootType
    }

    fun setRootType(rootType: String) {
        this.rootType = rootType
    }

    fun getTeaserUrl(): String? {
        return teaserUrl
    }

    fun setTeaserUrl(teaserUrl: String) {
        this.teaserUrl = teaserUrl
    }

    fun getFollower(): Any? {
        return follower
    }

    fun setFollower(follower: Any) {
        this.follower = follower
    }

    fun getClientValue(): Int? {
        return clientValue
    }

    fun setClientValue(clientValue: Int?) {
        this.clientValue = clientValue
    }
}