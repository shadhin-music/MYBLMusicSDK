package com.shadhinmusiclibrary.fragments.artist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.data.repository.ArtistAlbumContentRepository



class ArtistAlbumViewModelFactory(private val artistAlbumContentRepository: ArtistAlbumContentRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return ArtistAlbumsViewModel(artistAlbumContentRepository) as T
    }
}