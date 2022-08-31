package com.shadhinmusiclibrary.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.SortDescription
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class HomeViewModel(private val homeContentRepository: HomeContentRepository) : ViewModel() {

    private val _homeContent: MutableLiveData<List<SortDescription>> = MutableLiveData()
    val homeContent: LiveData<List<SortDescription>> = _homeContent

    fun fetchHomeData(pageNumber: Int?, isPaid: Boolean?) = viewModelScope.launch {
        val response = homeContentRepository.fetchHomeData(pageNumber, isPaid)
        if (response.status == Status.SUCCESS) {
            _homeContent.postValue(response.data!!.data)
        }
    }
}