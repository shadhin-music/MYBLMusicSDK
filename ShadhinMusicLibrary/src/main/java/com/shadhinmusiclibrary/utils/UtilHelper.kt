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
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistContentDataModel
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
                        artistName = artistName,
                        date = "",
                        contentType = content_Type,
                        userPlayListId = "",
                        episodeId = "",
                        starring = "",
                        seekable = false,
                        details = "",
                        totalStream = 0L,
                        rootId = rootContentId,
                        rootImage = rootImage,
                        rootType = rootContentType,
                        rootTitle = titleName
                    )
                )
            }
        }
        return musicList
    }

    fun getSongDetailToDownloadedSongDetailList(trackList: MutableList<DownloadedContent>): MutableList<SongDetailModel> {
        val songDetailList = mutableListOf<SongDetailModel>()
        for (trackItem in trackList) {
            trackItem.apply {
                songDetailList.add(
                    SongDetailModel().apply {
                        content_Id = contentId
                        imageUrl = rootImg
                        titleName = rootTitle
                        content_Type = rootType
                        playingUrl = "" + track
                        artist = artist
                        total_duration = timeStamp
                        copyright = ""
                        labelname = ""
                        releaseDate = ""
                        fav = ""
                        album_Id = ""
                        album_Id = rootId
                        rootContentId = rootId
                        rootContentType = rootType
                        rootImage = rootImg
                    }
                )
            }
        }
        return songDetailList
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
                    }
                )
            }
        }

        return songDetailList
    }

    fun getSongDetailToMusic(mMusic: Music): SongDetailModel {
        mMusic.apply {
//            val songDetail = SongDetail()
            return SongDetailModel().apply {
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
            }
        }
    }

    fun getSongDetailAndRootData(
        mSongDet: SongDetailModel,
        rootPatch: HomePatchDetailModel
    ): SongDetailModel {
        mSongDet.apply {
            rootContentId = rootPatch.ContentID
            rootContentType = rootPatch.ContentType
            rootImage = rootPatch.image
        }
        return mSongDet
    }

    fun getIMusicModelAndRootData(
        mSongDet: MutableList<SearchDataModel>
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
                }
            )
        }
        return iMusicData
    }

    fun getArtistContentDataToRootData(
        mSongDet: ArtistContentDataModel,
        rootPatch: HomePatchDetailModel
    ): ArtistContentDataModel {
        mSongDet.apply {
            mSongDet.apply {
                artist_Id = album_Id

                rootContentId = rootPatch.ContentID
                rootContentType = rootPatch.ContentType
                rootImage = rootPatch.image
            }
            return mSongDet
        }
    }

    fun getTrackToRootData(
        mSongTrack: SongTrackModel,
        rootPatch: HomePatchDetailModel
    ): SongTrackModel {
        mSongTrack.apply {
            rootContentId = rootPatch.ContentID
            rootContentType = rootPatch.ContentType
            rootImage = rootPatch.image
        }
        return mSongTrack
    }

    fun getSearchDataToRootData(
        rootPatch: CommonSearchData
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
                    })
                }
            }
    }

    fun getHomePatchDetailToData(podcastDetails: PodcastDetailsModel) = HomePatchDetailModel(
        "0",
        "",
        "",
        podcastDetails.ArtistName,
        podcastDetails.Id,
        "",
        "",
        podcastDetails.Id,
        "A",
        "",
        "0",
        podcastDetails.Follower,
        false,
        "",
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        false,
        "",
        "",
        "",
        "",
        podcastDetails.Image,
        "",
        ""
    )

    fun getHomePatchDetailToSearchDataModel(searchData: IMusicModel) = HomePatchDetailModel(
        AlbumId = searchData.album_Id ?: "",
        ArtistId = searchData.content_Id ?: "",
        ContentID = searchData.content_Id ?: "",
        ContentType = searchData.content_Type ?: "",
        PlayUrl = searchData.playingUrl ?: "",
        AlbumName = searchData.titleName ?: "",
        AlbumImage = "",
        fav = "",
        Banner = "",
        Duration = searchData.total_duration ?: "",
        TrackType = "",
        image = searchData.imageUrl ?: "",
        ArtistImage = "",
        Artist = searchData.artistName ?: "",
        CreateDate = "",
        Follower = "",
        imageWeb = "",
        IsPaid = false,
        NewBanner = "",
        PlayCount = 0,
        PlayListId = "",
        PlayListImage = "",
        PlayListName = "",
        RootId = "",
        RootType = "",
        Seekable = false,
        TeaserUrl = "",
        title = searchData.titleName ?: "",
        Type = ""
    )

    fun getHomePatchItemToData(data: List<PodcastDetailsModel>): HomePatchItemModel {
        val mPatchDetail = mutableListOf<HomePatchDetailModel>()
        for (patchItem in data) {
            mPatchDetail.add(
                HomePatchDetailModel(
                    "0",
                    "",
                    "",
                    patchItem.ArtistName,
                    patchItem.Id,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "0",
                    patchItem.Follower,
                    false,
                    "",
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    "",
                    "",
                    "",
                    "",
                    patchItem.Image,
                    "",
                    ""
                )
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
        albumModel.apply {
            return HomePatchDetailModel(
                AlbumId = album_Id ?: "",
                ArtistId = album_Id ?: "",
                ContentID = content_Id ?: "",
                ContentType = content_Type ?: "",
                PlayUrl = playingUrl ?: "",
                AlbumName = titleName ?: "",
                AlbumImage = "",
                fav = fav ?: "",
                Banner = "",
                Duration = total_duration ?: "",
                TrackType = "",
                image = imageUrl ?: "",
                ArtistImage = "",
                Artist = artistName ?: "",
                CreateDate = "",
                Follower = "",
                imageWeb = "",
                IsPaid = false,
                NewBanner = "",
                PlayCount = 0,
                PlayListId = "",
                PlayListImage = "",
                PlayListName = "",
                RootId = "",
                RootType = "",
                Seekable = false,
                TeaserUrl = "",
                title = "",
                Type = ""
            )
        }
    }

    fun getHomePatchDetailToSongDetail(songDetail: IMusicModel): HomePatchDetailModel {
        songDetail.apply {
            return HomePatchDetailModel(
                AlbumId = album_Id ?: content_Id!!,
                ArtistId = artist_Id ?: "",
                ContentID = content_Id ?: "",
                ContentType = "",
                PlayUrl = "",
                AlbumName = "",
                AlbumImage = "",
                fav = "",
                Banner = "",
                Duration = "",
                TrackType = "",
                image = imageUrl ?: "",
                ArtistImage = "",
                Artist = artistName ?: "",
                CreateDate = "",
                Follower = "",
                imageWeb = "",
                IsPaid = false,
                NewBanner = "",
                PlayCount = 0,
                PlayListId = "",
                PlayListImage = "",
                PlayListName = "",
                RootId = "",
                RootType = "",
                Seekable = false,
                TeaserUrl = "",
                title = titleName ?: "",
                Type = ""
            )
        }
    }

    fun getHomePatchDetailToFeaturedPodcastDetails(episode: FeaturedPodcastDetailsModel): HomePatchDetailModel {
        episode.apply {
            return HomePatchDetailModel(
                AlbumId = EpisodeId,
                AlbumImage = "",
                AlbumName = EpisodeName,
                Artist = "",
                ArtistId = "",
                ArtistImage = "",
                Banner = "",
                ContentID = EpisodeId,
                ContentType = ContentType,
                CreateDate = "",
                Duration = "",
                Follower = "",
                IsPaid = false,
                NewBanner = "",
                PlayCount = 0,
                PlayListId = "",
                PlayListImage = "",
                PlayListName = "",
                PlayUrl = PlayUrl,
                RootId = "",
                RootType = "",
                Seekable = false,
                TeaserUrl = "",
                TrackType = "",
                Type = "",
                fav = "",
                image = ImageUrl,
                imageWeb = ImageUrl,
                title = TrackName
            )
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
        aaa: List<IMusicModel>
    ): List<IMusicModel> {
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