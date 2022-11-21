package com.shadhinmusiclibrary.download.room.favDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shadhinmusiclibrary.data.model.fav.FavData


@Dao
internal interface FavoriteContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(favData: List<FavData>)


    @Query("SELECT * FROM FavData WHERE fav=1")
    fun getAllFavorites(): List<FavData>

    @Query("SELECT * FROM  FavData where content_Id = :id AND Fav=1 ")
    fun getFavoriteById(id: String): List<FavData>

    @Query("SELECT * FROM FavData WHERE content_Type='V'  ORDER By total_duration DESC ")
    fun getAllVideosFav(): List<FavData>

    @Query("SELECT * FROM FavData WHERE content_Type='S'  ORDER By total_duration DESC ")
    fun getAllSongsFav(): List<FavData>

    @Query("SELECT * FROM FavData WHERE content_Type='A'  ORDER By total_duration DESC ")
    fun getArtistFav(): List<FavData>

    @Query("SELECT * FROM FavData WHERE content_Type='R'  ORDER By total_duration DESC ")
    fun getAlbumsFav(): List<FavData>

    @Query("SELECT * FROM FavData WHERE content_Type='P'  ORDER By total_duration DESC ")
    fun getPlaylistFav(): List<FavData>

    @Query("SELECT * FROM FavData WHERE content_Type='PDJG'  ORDER By total_duration DESC ")
    fun getAllPodcastFav(): List<FavData>

    //
//    @Query("SELECT * FROM DownloadedContent where contentId = :id")
//    fun getDownloadById(id:String):List<DownloadedContent>
//
//    @Query("SELECT * FROM DownloadedContent where rootId = :albumId")
//    fun getDownloadsByAlbumId(albumId:String):List<DownloadedContent>
//
    @Query("DELETE FROM FavData WHERE content_Id = :id ")
    fun deleteFavoriteById(id: String)

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