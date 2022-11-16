package com.shadhinmusiclibrary.fragments.fav.favDataBase

import android.util.Log
import androidx.room.*
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.download.room.DownloadedContent


@Dao
interface FavoriteContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(favData: List<FavData>)


    @Query("SELECT * FROM FavData WHERE fav=1")
    fun getAllFavorites(): List<FavData>

    @Query("SELECT * FROM  FavData where contentId = :id AND Fav=1 ")
    fun getFavoriteById(id:String):List<FavData>

    @Query("SELECT * FROM FavData WHERE contentType='V'  ORDER By duration DESC ")
    fun getAllVideosFav():List<FavData>

    @Query("SELECT * FROM FavData WHERE contentType='S'  ORDER By duration DESC ")
    fun getAllSongsFav():List<FavData>

    @Query("SELECT * FROM FavData WHERE contentType='A'  ORDER By duration DESC ")
    fun getArtistFav():List<FavData>

    @Query("SELECT * FROM FavData WHERE contentType='R'  ORDER By duration DESC ")
    fun getAlbumsFav():List<FavData>

    @Query("SELECT * FROM FavData WHERE contentType='P'  ORDER By duration DESC ")
    fun getPlaylistFav():List<FavData>

    @Query("SELECT * FROM FavData WHERE contentType='PDJG'  ORDER By duration DESC ")
    fun getAllPodcastFav():List<FavData>
//
//    @Query("SELECT * FROM DownloadedContent where contentId = :id")
//    fun getDownloadById(id:String):List<DownloadedContent>
//
//    @Query("SELECT * FROM DownloadedContent where rootId = :albumId")
//    fun getDownloadsByAlbumId(albumId:String):List<DownloadedContent>
//
       @Query("DELETE FROM FavData WHERE contentId = :id ")
       fun deleteFavoriteById(id:String):Unit
      @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insert(favData: FavData)
//    @Query("DELETE FROM DownloadedContent WHERE contentId = :albumId")
//    fun deleteDownloadByAlbumIdId(albumId:String):Unit
//
//    @Query("SELECT COUNT(*) FROM DownloadedContent WHERE type IS NULL")
//    fun getPendingDownloadCount():Int
//
//    @Query("UPDATE DownloadedContent SET track = :path  WHERE contentId =:id")
//    fun setPath(id:String,path:String)
//
////    @Query("SELECT * from DownloadedContent WHERE path IS NOT NULL GROUP BY albumId ORDER By createTime DESC ")
////    fun getAllAlbums():List<DownloadedContent>
//
//    @Query("SELECT track FROM DownloadedContent where contentId = :contentId  AND isDownloaded = 1")
//    fun getTrackById(contentId:String):String
//
//    @Query("SELECT * FROM DownloadedContent where  isDownloaded = 1")
//    fun getAllDownloadedTrackById():List<DownloadedContent>
//
//    @Query("SELECT isDownloaded FROM downloadedcontent WHERE  contentId = :contentId AND isDownloaded =1 LIMIT 1")
//    fun downloadedContent(contentId: String):Boolean?
//
//    @Query("SELECT isDownloaded FROM downloadedcontent WHERE type = 'V' AND contentId = :contentId AND isDownloaded =1 LIMIT 1")
//     fun downloadedVideoContent(contentId: String):Boolean?
//
//     @Query("UPDATE downloadedcontent SET isDownloaded = 1 WHERE contentId = :contentId")
//     fun downloadCompleted(contentId: String)

//
//    @Query("SELECT track FROM DownloadedContent where contentId = :contentId & isDownloaded = 1")
//    fun getDownloadedTrackById(contentId:String):String

//    @Query("DELETE FROM DownloadedContent")
//    fun deleteAllDownloads():Unit

}