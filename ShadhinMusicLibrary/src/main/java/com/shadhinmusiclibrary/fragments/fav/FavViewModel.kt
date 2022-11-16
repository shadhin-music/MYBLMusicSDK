package com.shadhinmusiclibrary.fragments.fav


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.fav.FavDataModel

import com.shadhinmusiclibrary.data.repository.CreatePlaylistRepository
import com.shadhinmusiclibrary.data.repository.FavContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch
import org.json.JSONObject


internal class FavViewModel (private val favContentRepository: FavContentRepository): ViewModel() {

    private val _getFavContentAlbum: MutableLiveData<FavDataModel> = MutableLiveData()
    val getFavContentAlbum: LiveData<FavDataModel> = _getFavContentAlbum

    private val _addFavContent: MutableLiveData<FavDataModel> = MutableLiveData()
    val addFavContent: LiveData<FavDataModel> = _addFavContent

    private val _deleteFavContent: MutableLiveData<FavDataModel> = MutableLiveData()
    val deleteFavContent: LiveData<FavDataModel> = _deleteFavContent

    private val _getFavContentVideo: MutableLiveData<FavDataModel> = MutableLiveData()
    val getFavContentVideo: LiveData<FavDataModel> =  _getFavContentVideo

    private val _getFavContentSong: MutableLiveData<FavDataModel> = MutableLiveData()
    val getFavContentSong: LiveData<FavDataModel> =  _getFavContentSong

    private val _getFavContentPlaylist: MutableLiveData<FavDataModel> = MutableLiveData()
    val getFavContentPlaylist: LiveData<FavDataModel> =  _getFavContentPlaylist
    private val _getFavContentArtist: MutableLiveData<FavDataModel> = MutableLiveData()
    val getFavContentArtist: LiveData<FavDataModel> =_getFavContentArtist
    private val _getFavContentPodcast: MutableLiveData<FavDataModel> = MutableLiveData()
    val getFavContentPodcast: LiveData<FavDataModel> =_getFavContentPodcast

    fun getFavContentSong(type: String) = viewModelScope.launch {
        val response = favContentRepository.fetchAllFavoriteByType("S")
        _getFavContentSong.postValue(response.data)
    }
    fun getFavContentPlaylist(type: String) = viewModelScope.launch {
        val response = favContentRepository.fetchAllFavoriteByType("P")
        _getFavContentPlaylist.postValue(response.data)
    }
    fun getFavContentArtist(type: String) = viewModelScope.launch {
        val response = favContentRepository.fetchAllFavoriteByType("A")
        _getFavContentArtist.postValue(response.data)
    }
    fun getFavContentAlbum(type: String) = viewModelScope.launch {
        val response = favContentRepository.fetchAllFavoriteByType("R")
        _getFavContentAlbum.postValue(response.data)
    }
    fun getFavContentPodcast(type: String) = viewModelScope.launch {
        val response = favContentRepository.fetchAllFavoriteByType("PD")
         _getFavContentPodcast.postValue(response.data)
    }
    fun getFavContentVideo(type: String) = viewModelScope.launch {
        val response = favContentRepository.fetchAllFavoriteByType("V")
        _getFavContentVideo.postValue(response.data)
    }

    fun addFavContent(contentId:String,contentType: String) = viewModelScope.launch {
        val response = favContentRepository.addFavByType(contentId,contentType)
         _addFavContent.postValue(response.data)
    }
    fun deleteFavContent(contentId:String,contentType: String) = viewModelScope.launch {
        val response = favContentRepository.deleteFavByType(contentId,contentType)
        _deleteFavContent.postValue(response.data)
    }
}