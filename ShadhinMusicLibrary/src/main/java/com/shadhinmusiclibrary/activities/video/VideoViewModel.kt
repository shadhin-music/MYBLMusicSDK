package com.shadhinmusiclibrary.activities.video

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shadhinmusiclibrary.data.model.Video
import java.util.ArrayList


open class VideoViewModel :ViewModel() {

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
    fun videos(videoList: ArrayList<Video>?) {
        _videoListLiveData.value = videoList
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