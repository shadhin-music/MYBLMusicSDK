package com.shadhinmusiclibrary.data.model

import android.os.Parcelable
import androidx.annotation.Keep

@Keep
internal data class FeaturedPodcastDetails(
    val About: String,
    val CeateDate: String,
    val ContentType: String,
    val Duration: String,
    val EndDate: String,
    val EpisodeCode: String,
    val EpisodeId: String,
    val EpisodeName: String,
    val ImageUrl: String,
    val IsComingSoon: Boolean,
    val IsPaid: Boolean,
    val PatchType: String,
    val PlayUrl: String,
    val Presenter: String,
    val Seekable: Boolean,
    val ShowCode: String,
    val ShowId: String,
    val ShowName: String,
    val Sort: Int,
    val StartDate: String,
    val TotalPlayCount: Int,
    val TrackName: String,
    val TrackType: String,
    val TracktId: String,
    val fav: String
){
    fun getImageUrl300Size(): String {
        return this.ImageUrl.replace("<\$size\$>", "300")
    }
}