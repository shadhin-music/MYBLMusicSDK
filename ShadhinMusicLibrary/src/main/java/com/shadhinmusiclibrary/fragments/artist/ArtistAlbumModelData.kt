package com.shadhinmusiclibrary.fragments.artist

import androidx.annotation.Keep
import com.shadhinmusiclibrary.data.IMusicModel
import java.io.Serializable

@Keep
internal class ArtistAlbumModelData : IMusicModel {
    override var album_Id: String? = null
    override var artist_Id: String? = null
    override var content_Id: String? = null
    override var content_Type: String? = null
    override var playingUrl: String? = null
    var TotalPlay: Int? = null
    override var artistName: String? = null
    var copyright: String? = null
    override var total_duration: String? = null
    var fav: String? = null
    override var imageUrl: String? = null
    var labelname: String? = null
    var releaseDate: String? = null
    override var titleName: String? = null

    override var bannerImage: String? = null
    override var album_Name: String? = null
    override var rootContentId: String? = null
    override var rootContentType: String? = null
    override var rootImage: String? = null
    override var isPlaying: Boolean = false
}