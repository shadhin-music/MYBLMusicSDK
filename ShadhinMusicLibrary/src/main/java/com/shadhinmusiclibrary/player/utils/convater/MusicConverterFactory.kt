package com.shadhinmusiclibrary.player.utils.convater

import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.player.data.model.Music

class MusicConverterFactory private constructor(private val obj:Any){
    private  var converter:MusicConverter?=null
    init { initialization() }
    private fun initialization(){
        converter = when(obj){
            is ArtistContentData -> ArtistContentToMusic(obj)
            else -> null
        }
    }
    fun convert(): Music {
       return converter!!.convert()
    }
    companion object{
        fun Any.toMusic(): Music {
            return MusicConverterFactory(this).convert()
        }
    }
}
