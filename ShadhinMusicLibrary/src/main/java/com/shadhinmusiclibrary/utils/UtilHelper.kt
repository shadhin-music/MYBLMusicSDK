package com.shadhinmusiclibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.Uri
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.data.model.search.CommonSearchData
import com.shadhinmusiclibrary.data.model.search.SearchDataModel
import com.shadhinmusiclibrary.data.model.ArtistContentDataModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.create_playlist.UserSongsPlaylistDataModel
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.data.model.Music
import java.io.File

internal object UtilHelper {

    fun getScreenHeightWidth(context: Context, type: Int): Int {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        return if (type == 0) {
            width
        } else {
            height
        }
    }

    fun getScreenSize(context: Context): Point? {
        //val display = (context as Activity).windowManager.defaultDisplay
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    fun getImageUrlSize300(imageUrl: String): String {
        return imageUrl.replace("<\$size\$>", "300")
    }

    fun getMusicListToSongDetailList(mSongDetails: MutableList<IMusicModel>): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        for (songItem in mSongDetails) {
            songItem.apply {
                val newPlayUrl = if (playingUrl!!.contains(
                        "http",
                        true
                    )
                ) playingUrl!! else Constants.FILE_BASE_URL + playingUrl!!
                musicList.add(
                    Music(
                        mediaId = content_Id,
                        title = titleName,
                        displayDescription = "",
                        displayIconUrl = getImageUrlSize300(imageUrl!!),
                        mediaUrl = Constants.FILE_BASE_URL + playingUrl,
                        artistName = artistName ?: "",
                        date = "",
                        contentType = content_Type,
                        userPlayListId = "",
                        episodeId = "",
                        starring = "",
                        seekable = isSeekAble,
                        details = "",
                        fav = "" /*fav value set for this song are radio or normal song*/,
                        totalStream = 0L,
                        rootId = rootContentId,
                        rootImage = rootImage,
                        rootType = rootContentType,
                        rootTitle = titleName ?: ""
                    )
                )
            }
        }
        return musicList
    }

    fun getSongDetailToMusicList(musicList: MutableList<Music>): MutableList<IMusicModel> {
        val songDetailList = mutableListOf<IMusicModel>()
        for (musicItem in musicList) {
            musicItem.apply {
                songDetailList.add(
                    SongDetailModel().apply {
                        content_Id = mediaId ?: ""
                        imageUrl = displayIconUrl ?: ""
                        titleName = title ?: ""
                        content_Type = contentType ?: ""
                        playingUrl = mediaUrl ?: ""
                        artistName = artistName ?: ""
                        total_duration = date ?: ""
                        copyright = ""
                        labelname = ""
                        releaseDate = ""
                        fav = ""
                        artist_Id = ""
                        album_Id = ""
                        userPlayListId = userPlayListId ?: ""
                        rootContentId = rootId ?: ""
                        rootContentType = rootType ?: ""
                        rootImage = rootImage ?: ""
                        isSeekAble = musicItem.seekable
                    }
                )
            }
        }
        return songDetailList
    }

    fun getSongDetailToMusic(mMusic: Music): SongDetailModel {
//            val songDetail = SongDetail()
        return SongDetailModel().apply {
            content_Id = mMusic.mediaId ?: ""
            imageUrl = mMusic.displayIconUrl ?: ""
            titleName = mMusic.title ?: ""
            content_Type = mMusic.contentType ?: ""
            playingUrl = mMusic.mediaUrl ?: ""
            artistName = mMusic.artistName
            total_duration = mMusic.date ?: ""
            copyright = ""
            labelname = ""
            releaseDate = ""
            fav = ""
            artist_Id = ""
            album_Id = ""
            rootContentId = mMusic.rootId ?: ""
            rootContentType = mMusic.rootType ?: ""
            rootImage = mMusic.rootImage ?: ""
            isSeekAble = mMusic.seekable
            /*fav value set for this song are radio or normal song*/
        }
    }

    fun getEmptyHomePatchDetail() = HomePatchDetailModel().apply {
        isSeekAble = true
    }

    fun getMixdUpIMusicWithRootData(
        mSongDet: IMusicModel,
        rootPatch: HomePatchDetailModel,
    ): IMusicModel {
        mSongDet.apply {
            rootContentId = rootPatch.content_Id
            rootContentType = rootPatch.content_Type
            rootImage = rootPatch.imageUrl
        }
        return mSongDet
    }

    fun getRadioSong(
        mSongDet: SongDetailModel,
    ): SongDetailModel {
        mSongDet.apply {
            rootContentId = mSongDet.rootContentId
            content_Id = mSongDet.content_Id
            rootContentType = mSongDet.content_Type
            rootImage = mSongDet.imageUrl
        }
        return mSongDet
    }

    fun getHomeRadioSong(
        rootPatch: HomePatchDetailModel,
    ): IMusicModel {
        return HomePatchDetailModel().apply {
            content_Id = rootPatch.content_Id
            rootContentId = rootPatch.rootContentId
            content_Type = rootPatch.content_Type
            imageUrl = rootPatch.imageUrl
            titleName = rootPatch.titleName
        }
    }

    fun getIMusicModelAndRootData(
        mSongDet: MutableList<SearchDataModel>,
    ): MutableList<IMusicModel> {
        val iMusicData = mutableListOf<IMusicModel>()
        for (seaDataItem in mSongDet) {
            iMusicData.add(
                SearchDataModel().apply {
                    content_Id = seaDataItem.content_Id
                    imageUrl = seaDataItem.imageUrl
                    imageWeb = seaDataItem.imageWeb
                    titleName = seaDataItem.titleName
                    content_Type = seaDataItem.content_Type
                    playingUrl = seaDataItem.playingUrl
                    total_duration = seaDataItem.total_duration
                    fav = seaDataItem.fav
                    bannerImage = seaDataItem.bannerImage
                    playCount = seaDataItem.playCount
                    type = seaDataItem.type
                    isPaid = seaDataItem.isPaid
                    seekable = seaDataItem.seekable
                    trackType = seaDataItem.trackType
                    artist_Id = seaDataItem.artist_Id
                    artistName = seaDataItem.artistName
                    album_Id = seaDataItem.album_Id
                    rootContentId = seaDataItem.album_Id
                    rootContentType = seaDataItem.content_Type
                    isSeekAble = true
                }
            )
        }
        return iMusicData
    }

    /* fun getSongDetailAndRootDataForUSERPLAYLIST(
         mSongDet: UserSongsPlaylistDataModel
     ): SongDetailModel {
         mSongDet.apply {
             return SongDetailModel().apply {
                 content_Id = contentID.toString()
                 imageUrl = image.toString()
                 titleName = title.toString()
                 content_Type = contentType.toString()
                 playingUrl = playUrl.toString()
                 artist = artist.toString()
                 duration = duration.toString()
                 copyright = copyright.toString()
                 labelname = labelname.toString()
                 releaseDate = releaseDate.toString()
                 fav = ""
                 artist_Id = artistId.toString()
                 albumId = albumId.toString()
                 userPlayListId = userPlayListId
                 rootContentId = contentID.toString()
                 rootContentType = ""
                 rootImage = ""
             }
         }
     }*/

    fun getArtistContentDataToRootData(
        mSongDet: ArtistContentDataModel,
        rootPatch: HomePatchDetailModel,
    ): ArtistContentDataModel {
        mSongDet.apply {
            mSongDet.apply {
                artist_Id = album_Id
                rootContentId = rootPatch.content_Id
                rootContentType = rootPatch.content_Type
                rootImage = rootPatch.imageUrl
                isSeekAble = true
            }
            return mSongDet
        }
    }

    fun getTrackToRootData(
        mSongTrack: SongTrackModel,
        rootPatch: HomePatchDetailModel,
    ): SongTrackModel {
        mSongTrack.apply {
            rootContentId = rootPatch.content_Id
            rootContentType = rootPatch.content_Type
            rootImage = rootPatch.imageUrl
            isSeekAble = true
        }
        return mSongTrack
    }

    fun getSearchDataToRootData(
        rootPatch: CommonSearchData,
    ): MutableList<IMusicModel> {
        return mutableListOf<IMusicModel>()
            .apply {
                for (seDaMoItem in rootPatch.data) {
                    add(SearchDataModel().apply {
                        content_Id = seDaMoItem.content_Id
                        imageUrl = seDaMoItem.imageUrl
                        imageWeb = seDaMoItem.imageWeb
                        titleName = seDaMoItem.titleName
                        content_Type = seDaMoItem.content_Type
                        playingUrl = seDaMoItem.playingUrl
                        total_duration = seDaMoItem.total_duration
                        fav = seDaMoItem.fav
                        bannerImage = seDaMoItem.bannerImage
                        playCount = seDaMoItem.playCount
                        type = seDaMoItem.type
                        isPaid = seDaMoItem.isPaid
                        seekable = seDaMoItem.seekable
                        trackType = seDaMoItem.trackType
                        artist_Id = seDaMoItem.artist_Id
                        artistName = seDaMoItem.artistName
                        album_Id = seDaMoItem.album_Id
                        rootContentId = seDaMoItem.album_Id
                        rootContentType = rootPatch.type
                        isSeekAble = true
                    })
                }
            }
    }

    fun getHomePatchDetailToData(podcastDetails: PodcastDetailsModel) =
        HomePatchDetailModel().apply {
            artistName = podcastDetails.ArtistName
            content_Id = podcastDetails.Id ?: ""
            album_Id = podcastDetails.Id ?: ""
            artist_Id = podcastDetails.Id ?: ""
            content_Type = "A"
            fav = "0"
            follower = podcastDetails.Follower
            imageUrl = podcastDetails.Image
        }

    fun getHomePatchDetailToSearchDataModel(searchData: IMusicModel) =
        HomePatchDetailModel().apply {
            album_Id = searchData.album_Id ?: ""
            artist_Id = searchData.content_Id ?: ""
            content_Id = searchData.content_Id ?: ""
            content_Type = searchData.content_Type ?: ""
            playingUrl = searchData.playingUrl ?: ""
            album_Name = searchData.titleName ?: ""
            total_duration = searchData.total_duration ?: ""
            imageUrl = searchData.imageUrl ?: ""
            artistName = searchData.artistName ?: ""
            isSeekAble = true
            titleName = searchData.titleName ?: ""
        }

    fun getHomePatchItemToData(data: List<PodcastDetailsModel>): HomePatchItemModel {
        val mPatchDetail = mutableListOf<HomePatchDetailModel>()
        for (patchItem in data) {
            mPatchDetail.add(
                HomePatchDetailModel().apply {
                    titleName = patchItem.ArtistName
                    artistName = patchItem.ArtistName
                    content_Id = patchItem.Id
                    album_Id = patchItem.Id
                    artist_Id = patchItem.Id
                    content_Type = "A"
                    fav = "0"
                    follower = patchItem.Follower
                    imageUrl = patchItem.Image
                }
            )
        }
        return HomePatchItemModel(
            "",
            "",
            mPatchDetail,
            "",
            "",
            0,
            0
        )
    }

    fun getHomePatchDetailToAlbumModel(albumModel: ArtistAlbumModelData): HomePatchDetailModel {
        return HomePatchDetailModel().apply {
            album_Id = albumModel.album_Id ?: ""
            artist_Id = albumModel.artist_Id ?: ""
            content_Id = albumModel.content_Id
            content_Type = albumModel.content_Type ?: ""
            playingUrl = albumModel.playingUrl ?: ""
            album_Name = albumModel.titleName ?: ""
            total_duration = albumModel.total_duration ?: ""
            imageUrl = albumModel.imageUrl ?: ""
            artistName = albumModel.artistName ?: ""
        }
    }

    fun getHomePatchDetailToSongDetail(songDetail: IMusicModel): HomePatchDetailModel {
        return HomePatchDetailModel().apply {
            album_Id = songDetail.album_Id ?: ""
            artist_Id = songDetail.artist_Id ?: ""
            content_Id = songDetail.content_Id
            imageUrl = songDetail.imageUrl ?: ""
            artistName = songDetail.artistName ?: ""
            titleName = songDetail.titleName ?: ""
        }
    }

    fun getHomePatchDetailToFeaturedPodcastDetails(episode: FeaturedPodcastDetailsModel): HomePatchDetailModel {
        episode.apply {
            return HomePatchDetailModel().apply {
                album_Id = EpisodeId
                album_Name = EpisodeName
                content_Id = EpisodeId
                content_Type = ContentType
                playingUrl = PlayUrl
                imageUrl = ImageUrl
                imageWeb = ImageUrl
                titleName = TrackName
            }
        }
    }

    fun getVideoToSearchData(data: SearchDataModel): VideoModel {
        data.apply {
            return VideoModel(
                albumId = album_Id,
                albumImage = albumImage,
                albumName = album_Name,
                artist = artistName,
                artistId = album_Id,
                artistImage = artistImage,
                banner = bannerImage,
                contentID = content_Id,
                contentType = content_Type,
                createDate = createDate,
                duration = total_duration,
                follower = follower,
                isPaid = isPaid,
                newBanner = newBanner,
                playCount = playCount,
                playListId = playListId,
                playListImage = playListImage,
                playListName = playListName,
                playUrl = playingUrl,
                rootId = rootContentId,
                rootType = rootContentType,
                seekable = seekable,
                teaserUrl = teaserUrl,
                trackType = trackType,
                type = rootContentType,
                fav = fav,
                image = imageUrl,
                imageWeb = imageWeb,
                title = titleName
            )
        }
    }

    fun getVideoToIMusic(data: IMusicModel): VideoModel {
        data.apply {
            return VideoModel(
                albumId = album_Id,
                albumImage = "",
                albumName = album_Name,
                artist = artistName,
                artistId = album_Id,
                artistImage = "",
                banner = bannerImage,
                contentID = content_Id,
                contentType = content_Type,
                createDate = "",
                duration = total_duration,
                follower = "",
                isPaid = false,
                newBanner = "",
                playCount = 0,
                playListId = "",
                playListImage = "",
                playListName = "",
                playUrl = playingUrl,
                rootId = rootContentId,
                rootType = rootContentType,
                seekable = false,
                teaserUrl = "",
                trackType = "",
                type = rootContentType,
                fav = "",
                image = imageUrl,
                imageWeb = "",
                title = titleName
            )
        }
    }

    fun getCurrentRunningSongToNewSongList(
        mediaId: String?,
        aaa: List<IMusicModel>,
    ): MutableList<IMusicModel> {
        val newList: MutableList<IMusicModel> = ArrayList()
        aaa.forEach {
            if (it.content_Id == mediaId) {
                it.isPlaying = true
                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
                newList.add(it)
            } else {
                it.isPlaying = false
                newList.add(it)
            }
        }
        return newList
    }

    fun deleteFileIfExists(uri: Uri) {
        val fdelete: File = File(uri.path)
        if (fdelete.exists()) {
            if (fdelete.delete()) {
            }
        }
    }
}