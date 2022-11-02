package com.shadhinmusiclibrary.utils
import android.view.View.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R

import com.shadhinmusiclibrary.adapter.AlbumsTrackAdapter
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



object DownloadProgressObserver {

    private var cacheRepository: CacheRepository? = null
    private var downloadObserverScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun setCacheRepository(cacheRepository: CacheRepository) {
        this.cacheRepository = cacheRepository
    }

    private val albumsTrackHolderMap: HashMap<String, AlbumsTrackAdapter.ViewHolder> =
        HashMap()


    fun updateProgressForAllHolder() {


        try {
            albumsTrackHolderMap.forEach {
                updateProgress(it.value)

            }

        } catch (e: Exception) {

        }

    }



    fun addViewHolder(holder: RecyclerView.ViewHolder, id: String) {
        when (holder) {
            is AlbumsTrackAdapter.ViewHolder -> {
                if (holder.tag != null) {
                    albumsTrackHolderMap.remove(holder.tag)
                    //Log.e("TAGDOWNLOADED","TAGDOWNLOADED123: " + holder.tag )
                }
                holder.tag = id
                albumsTrackHolderMap[id] = holder
               // Log.e("TAGDOWNLOADED","TAGDOWNLOADED321: " + holder.tag )
            }

        }
    }

    fun updateProgress(holder: RecyclerView.ViewHolder) {
        downloadObserverScope.launch {
            withContext(Dispatchers.Main) {
                when (holder) {
                    is AlbumsTrackAdapter.ViewHolder -> {
                        val id = holder.tag ?: ""
                        val isDownloaded = cacheRepository?.isTrackDownloaded(id) ?: false
//                        Log.e("TAGDOWNLOADED","TAGDOWNLOADED: " + isDownloaded)
//                        Log.e("TAGDOWNLOADED","TAGDOWNLOADED: " + holder.tag )
                        if (isDownloaded) {
                            val imageView:ImageView = holder.itemView.findViewById(R.id.iv_song_type_icon)
                            val progress:CircularProgressIndicator = holder.itemView.findViewById(R.id.progress)
                            progress.visibility= GONE
                            imageView.visibility = VISIBLE
                        } else {
                            val imageView:ImageView = holder.itemView.findViewById(R.id.iv_song_type_icon)
                            val progress:CircularProgressIndicator = holder.itemView.findViewById(R.id.progress)
                            progress.visibility= VISIBLE
                            imageView.visibility = GONE
                              //val percent= currentProgress
//
////                            Log.e("TAGDOWNLOADED","TAGDOWNLOADEDtag: " + holder.tag )
//                            Log.e("TAGDOWNLOADED","TAGDOWNLOADEDpercent: " + percent )
//                            progress.setProgressCompat(
//                                percent,
//                                true
//                            )

                        }
                    }

                }
            }
        }

    }
}