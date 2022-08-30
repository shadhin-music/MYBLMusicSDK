//package com.shadhinmusiclibrary.player.data.local
//
//import android.content.Context
//import com.shadhinmusiclibrary.player.data.model.Music
//import com.shadhinmusiclibrary.player.data.model.MusicPosition
//
//
//class MusicPositionRepository(private val context: Context) {
//    //private  val database:MusicPositionDatabase by lazy { MusicPositionDatabase.newInstance(context) }
//    suspend fun savePosition(music: Music?, position:Long?=null, duration: Long?=null){
//        savePositionOnMainThread(music, position, duration)
//    }
//    fun savePositionOnMainThread(music: Music?, position: Long?=null, duration: Long?=null){
//
//
//        when{
//
//            //Insert
//            position == null && duration !=null && music?.isPodCast() == true && !music.isLive()->{
//                val mPosition = MusicPosition().apply {
//                    this.mediaId = music.mediaId
//                    this.duration = duration
//                }
//              //  database.dao().insert(mPosition)
//            }
//
//            //Update
//            position != null && duration == null  && music?.isPodCast() == true && !music.isLive()->{
//
//                if(position > 3){
//                   // database.dao().update(music.mediaId?:"",position)
//                }
//            }
//        }
//
//
//    }
//
////    suspend fun getSavedPosition(mediaId: String?): MusicPosition? {
////      // return database.dao().findMyMediaId(mediaId)
////    }
//
//
//}