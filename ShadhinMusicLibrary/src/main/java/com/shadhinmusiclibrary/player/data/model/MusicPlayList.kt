package com.shadhinmusiclibrary.player.data.model

import android.os.Bundle

import com.gm.shadhin.player.ShadhinMusicServiceConnection
import com.gm.shadhin.player.utils.makeValidUrl
import java.io.Serializable


data class MusicPlayList(var list: List<Music>, val defaultPosition:Int = 0): Serializable{
    fun isEmptyOrNull() = list.isNullOrEmpty()
    fun size() = list.size
    fun toBundle(command: ShadhinMusicServiceConnection.Command) = Bundle().apply {
        putSerializable(command.dataKey, this@MusicPlayList)
    }
    fun toBundle(key: String) = Bundle().apply {
        putSerializable(key, this@MusicPlayList)
    }
    fun isValidPlayList(): Boolean {

        list.forEach { music ->
            if(music.mediaUrl == null){
                return false
            }
        }
        return true
    }
    fun decodePlayUrl() {
        list.forEach { it.mediaUrl = it.mediaUrl.makeValidUrl() }
    }




    override fun toString(): String {
        return "Size: ${size()} List: $list"
    }

    override fun equals(other: Any?): Boolean {
        if(other is MusicPlayList){
            if(this.size() == other.size()){
                this.list.forEachIndexed { index, music ->
                    if(music != other.list[index]){
                        return false
                    }
                }
            }else{
                return false
            }
        }
        return true
    }


    override fun hashCode(): Int {
        var result = list.hashCode()
        result = 31 * result + defaultPosition
        return result
    }



    companion object{
        fun fromBundle(bundle: Bundle?, command: ShadhinMusicServiceConnection.Command) = bundle?.getSerializable(command.dataKey) as MusicPlayList
    }
}
