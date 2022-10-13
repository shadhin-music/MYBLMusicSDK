package com.shadhinmusiclibrary.fragments.artist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.data.repository.ArtistAlbumContentRepository
import com.shadhinmusiclibrary.data.repository.FeaturedTracklistRepository
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.data.repository.PopularArtistRepository


internal class FeaturedTracklistViewModelFactory(private val featuredTracklistRepository: FeaturedTracklistRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return FeaturedTracklistViewModel(featuredTracklistRepository) as T
    }
}