package com.shadhinmusiclibrary.download.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shadhinmusiclibrary.data.model.SongDetail
import java.io.Serializable


@Entity
data class WatchLaterContent(
    @PrimaryKey var contentId:String,
    var rootId:String,
    var rootImg:String,
    var rootTitle:String,
    var rootType:String,
    var track:String ?=null,
    var type:String,
    var isDownloaded:Int,
    var isFavorite:Int,
    var artist:String,
    var timeStamp:String
): Serializable {
    fun getImageUrl300Size(): String {
        return this.rootImg.replace("<\$size\$>", "300")
    }

}
