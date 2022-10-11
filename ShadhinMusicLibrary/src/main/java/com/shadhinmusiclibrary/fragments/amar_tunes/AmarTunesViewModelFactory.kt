package com.shadhinmusiclibrary.fragments.amar_tunes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.data.repository.AmartunesContentRepository
import com.shadhinmusiclibrary.data.repository.HomeContentRepository

class AmarTunesViewModelFactory(private val amartunesContentRepository: AmartunesContentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AmarTunesViewModel(amartunesContentRepository) as T
    }
}