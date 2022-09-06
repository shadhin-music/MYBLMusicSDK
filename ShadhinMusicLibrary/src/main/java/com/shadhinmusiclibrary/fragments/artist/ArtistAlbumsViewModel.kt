package com.shadhinmusiclibrary.fragments.artist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.repository.ArtistAlbumContentRepository
import com.shadhinmusiclibrary.data.repository.ArtistContentRepository
import com.shadhinmusiclibrary.data.repository.ArtistSongContentRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class ArtistAlbumsViewModel (private val artistAlbumRepository: ArtistAlbumContentRepository): ViewModel() {


    private val _artistAlbumContent: MutableLiveData<ArtistAlbumModel> = MutableLiveData()
    val artistAlbumContent: MutableLiveData<ArtistAlbumModel> = _artistAlbumContent


    fun fetchArtistAlbum(type: String,id:Int) = viewModelScope.launch {
        val response = artistAlbumRepository.fetchArtistAlbum(type,id)
        if (response.status == Status.SUCCESS) {
            _artistAlbumContent.postValue(response.data)
            Log.d("dfsfdsdfsdf","fetchArtistAlbumData :"+ response.data)
        }
        Log.i("dfsfdsdfsdf", "fetchArtistAlbumData: ${response.message}")
    }

}