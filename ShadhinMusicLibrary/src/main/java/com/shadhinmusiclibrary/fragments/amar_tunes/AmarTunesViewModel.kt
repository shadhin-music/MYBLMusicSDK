package com.shadhinmusiclibrary.fragments.amar_tunes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.data.model.RBT
import com.shadhinmusiclibrary.data.repository.AmartunesContentRepository

import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse

import kotlinx.coroutines.launch
import okhttp3.RequestBody

internal class AmarTunesViewModel(private val amartunesContentRepository: AmartunesContentRepository): ViewModel() {



    private val _urlContent:MutableLiveData<ApiResponse<RBT>> = MutableLiveData()
     val urlContent:LiveData<ApiResponse<RBT>> = _urlContent

    fun fetchRBTURL() = viewModelScope.launch {

        val response = amartunesContentRepository.rbtURL()
        Log.e("HOME", "PAGE CALLED "+ response)
        _urlContent.postValue(response)

    }
}