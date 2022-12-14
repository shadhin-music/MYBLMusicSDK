package com.shadhinmusiclibrary.fragments.artist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.data.repository.ArtistAlbumContentRepository
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.data.repository.PopularArtistRepository


class PopularArtistViewModelFactory(private val popularArtistRepository: PopularArtistRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return PopularArtistViewModel(popularArtistRepository) as T
    }
}