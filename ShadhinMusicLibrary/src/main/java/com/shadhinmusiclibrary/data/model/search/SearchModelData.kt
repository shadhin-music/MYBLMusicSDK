package com.shadhinmusiclibrary.data.model.search

import androidx.annotation.Keep

@Keep
internal data class SearchModelData(
    val Album: Album,
    val Artist: Artist,
    val PodcastEpisode: PodcastEpisode,
    val PodcastShow: PodcastShow,
    val PodcastTrack: PodcastTrack,
    val Track: Track,
    val Video: Video
)