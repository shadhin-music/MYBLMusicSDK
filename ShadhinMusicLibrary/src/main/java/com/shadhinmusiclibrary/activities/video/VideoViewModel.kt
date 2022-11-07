package com.shadhinmusiclibrary.activities.video

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.download.room.DatabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList


internal open class VideoViewModel(private val databaseClient: DatabaseClient?) :ViewModel() {

    private val _progressbarVisibility: MutableLiveData<Int> = MutableLiveData<Int>(View.GONE)
    val progressbarVisibility: LiveData<Int> = _progressbarVisibility

    private val _isListLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)
    val isListLiveData: LiveData<Boolean>  = _isListLiveData

    private val _videoListLiveData: MutableLiveData<List<Video>> = MutableLiveData()
    public val videoListLiveData: LiveData<List<Video>> = _videoListLiveData

    private val _currentVideo: MutableLiveData<Video> = MutableLiveData()
    public val currentVideo: LiveData<Video> = _currentVideo

    fun layoutToggle(){
        _isListLiveData.value = isListLiveData.value == false
    }
    fun videos(videoList: ArrayList<Video>?)  = viewModelScope.launch(Dispatchers.IO){

        videoList?.forEach {
            it.isDownloaded = isVideoDownloaded(it.contentID)
        }

        _videoListLiveData.postValue(videoList)
    }
    private suspend fun isVideoDownloaded(content:String?): Boolean {
        return databaseClient?.getDownloadDatabase()?.DownloadedContentDao()?.downloadedVideoContent(content?:"")?:false
    }
    fun currentVideo(mediaId: String?){
        val currentVideo = kotlin.runCatching { _videoListLiveData.value?.first { it.contentID == mediaId } }.getOrNull()
       if(currentVideo !=null) {
           _currentVideo.value = currentVideo
       }
    }
    private fun showLoader()  = _progressbarVisibility.postValue(View.VISIBLE)
    private fun hideLoader()  = _progressbarVisibility.postValue(View.GONE)


}