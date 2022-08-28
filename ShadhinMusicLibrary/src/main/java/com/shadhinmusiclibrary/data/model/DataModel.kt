package com.shadhinmusiclibrary.data.model

import android.provider.ContactsContract
import androidx.annotation.ColorInt


sealed class DataModel {
    data class Search(
        val name: String
    ) : DataModel()
    data class Artist(
        val name: String
    ) : DataModel()

    data class TopTrending(
        val name: String
    ) : DataModel()

    data class BrowseAll(
        val name: String

    ) : DataModel()
    data class Ad(
        val name: String
    ): DataModel()
    data class Download(
        val name: String
    ): DataModel()
    data class PopularAmarTunes(
       val name: String
    ): DataModel()
    data class PopularBands(
        val name:String
    ):DataModel()
    data class MadeForYou(
        val name:String
    ):DataModel()
    data class LatestRelease(
        val name: String
    ):DataModel()
    data class PopularPodcast(
        val name:String
    ):DataModel()
    data class BlOffers(
        val name: String
    ):DataModel()
    data class TrendingMusicVideo(
        val name: String
    ):DataModel()
}
