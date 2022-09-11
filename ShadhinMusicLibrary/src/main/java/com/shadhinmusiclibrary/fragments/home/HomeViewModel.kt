package com.shadhinmusiclibrary.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.HomeData

import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class HomeViewModel(private val homeContentRepository: HomeContentRepository): ViewModel() {

    private val _homeContent:MutableLiveData<ApiResponse<HomeData>> = MutableLiveData()
    val homeContent:LiveData<ApiResponse<HomeData>> = _homeContent

    fun fetchHomeData(client:Int?,pageNumber: Int?, isPaid: Boolean?) = viewModelScope.launch {
        Log.e("HOME", "PAGE CALLED "+pageNumber)
        val response = homeContentRepository.fetchHomeData(client!!,pageNumber, isPaid)

            _homeContent.postValue(response)

    }
}