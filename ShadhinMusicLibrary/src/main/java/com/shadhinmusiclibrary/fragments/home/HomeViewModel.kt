package com.shadhinmusiclibrary.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.HomeDataModel
import com.shadhinmusiclibrary.data.model.RBTModel
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch

internal class HomeViewModel(private val homeContentRepository: HomeContentRepository) :
    ViewModel() {
    private val _homeContent: MutableLiveData<ApiResponse<HomeDataModel>> = MutableLiveData()
    val homeContent: LiveData<ApiResponse<HomeDataModel>> = _homeContent
    private val _urlContent: MutableLiveData<ApiResponse<RBTModel>> = MutableLiveData()
    val urlContent: LiveData<ApiResponse<RBTModel>> = _urlContent

    fun fetchHomeData(pageNumber: Int?, isPaid: Boolean?) = viewModelScope.launch {
        val response = homeContentRepository.fetchHomeData(pageNumber, isPaid)
        _homeContent.postValue(response)
    }

    fun fetchRBTURL() = viewModelScope.launch {
        val response = homeContentRepository.rbtURL()
        _urlContent.postValue(response)
    }
}