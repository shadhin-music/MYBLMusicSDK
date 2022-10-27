package com.shadhinmusiclibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.podcast.SongTrack
import com.shadhinmusiclibrary.data.model.search.SearchData
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.data.model.Music

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

    /* fun getMusicToSongDetail(mSongDet: SongDetail): Music {
         mSongDet.apply {
             return Music(
                 mediaId = ContentID,
                 title = title,
                 displayDescription = "",
                 displayIconUrl = getImageUrl300Size(),
                 mediaUrl = Constants.FILE_BASE_URL + PlayUrl,
                 artistName = artist,
                 date = releaseDate,
                 contentType = ContentType,
                 userPlayListId = userPlayListId,
                 episodeId = "",
                 starring = "",
                 seekable = false,
                 details = "",
                 totalStream = 0L,
                 rootId = rootContentID,
                 rootImage = rootImage,
                 rootType = rootContentType,
                 rootTitle = title
             )
         }
     }*/

    fun getMusicListToSongDetailList(mSongDetails: MutableList<IMusicModel>): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        for (songItem in mSongDetails) {
            songItem.apply {
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

    /* fun getSongDetailToTrackList(trackList: MutableList<Track>): MutableList<SongDetail> {
         val songDetailList = mutableListOf<SongDetail>()
         for (trackItem in trackList) {
             trackItem.apply {
                 songDetailList.add(
                     SongDetail(
                         ContentID = Id.toString(),
                         image = ImageUrl,
                         title = Name,
                         ContentType = ContentType,
                         PlayUrl = PlayUrl,
                         artist = Starring,
                         duration = Duration,
                         copyright = "",
                         labelname = Name,
                         releaseDate = CeateDate,
                         fav = fav,
                         ArtistId = "",
                         albumId = "",
                         userPlayListId = "",
                         *//*rootType = ContentType,*//*

                        rootContentID = rootContentID,
                        rootContentType = rootContentType,
                        rootImage = rootImage
                    )
                )
            }
        }
        return songDetailList
    }*/

    /* fun getSongDetailToTopTrendingDataList(topTrandList: List<TopTrendingData>): MutableList<SongDetail> {
         val songDetailList = mutableListOf<SongDetail>()
         for (trackItem in topTrandList) {
             trackItem.apply {
                 songDetailList.add(
                     SongDetail(
                         ContentID = ContentID,
                         image = image,
                         title = title,
                         ContentType = ContentType,
                         PlayUrl = PlayUrl,
                         artist = artistname,
                         duration = duration,
                         copyright = "",
                         labelname = labelname,
                         releaseDate = "",
                         fav = "",
                         ArtistId = ArtistId,
                         albumId = AlbumId,
                         userPlayListId = "",
                         *//*rootType = ContentType,*//*

                        rootContentID = ContentID,
                        rootContentType = ContentType,
                        rootImage = image
                    )
                )
            }
        }
        return songDetailList
    }*/

    /* fun getSongDetailToSearchDataList(topTrandList: List<SearchData>): MutableList<SongDetail> {
         val songDetailList = mutableListOf<SongDetail>()
         for (trackItem in topTrandList) {
             trackItem.apply {
                 songDetailList.add(
                     SongDetail(
                         ContentID = ContentID,
                         image = image,
                         title = title,
                         ContentType = ContentType,
                         PlayUrl = PlayUrl ?: "",
                         artist = Artist,
                         duration = "120",
                         copyright = "",
                         labelname = "",
                         releaseDate = "",
                         fav = "",
                         ArtistId = ArtistId,
                         albumId = AlbumId,
                         userPlayListId = "",
                         *//*rootType = ContentType,*//*

                        *//*  rootContentID = trackItem.rootContentID,
                          rootContentType = trackItem.rootContentType,
                          rootImage = trackItem.rootImage*//*
                        rootContentID = ContentID,
                        rootContentType = ContentType,
                        rootImage = image
                    )
                )
            }
        }
        return songDetailList
    }*/

//    fun getSongDetailToFeaturedSongDetailList(trackList: MutableList<IMusicModel>): MutableList<IMusicModel> {
//        val songDetailList = mutableListOf<IMusicModel>()
//        for (trackItem in trackList) {
//            trackItem.apply {
//                songDetailList.add(
//                    trackItem
//                )
//            }
//        }
//        return songDetailList
//    }

    /* fun getSongDetailToArtistContentDataList(musicList: MutableList<ArtistContentData>): MutableList<SongDetail> {
         val songDetailList = mutableListOf<SongDetail>()
         for (musicItem in musicList) {
             musicItem.apply {
                 songDetailList.add(
                     SongDetail(
                         ContentID = ContentID,
                         image = image,
                         title = title,
                         ContentType = ContentType,
                         PlayUrl = PlayUrl,
                         artist = artistname,
                         duration = duration,
                         copyright = copyright,
                         labelname = labelname,
                         releaseDate = releaseDate,
                         fav = fav,
                         ArtistId = "",
                         albumId = "",
                         userPlayListId = "",
                         *//*rootType = rootContentType,*//*

                        rootContentID = rootContentID,
                        rootContentType = rootContentType,
                        rootImage = rootImage
                    )
                )
            }
        }

        return songDetailList
    }*/

    fun getSongDetailToMusicList(musicList: MutableList<Music>): MutableList<IMusicModel> {
        val songDetailList = mutableListOf<IMusicModel>()
        for (musicItem in musicList) {
            musicItem.apply {
                songDetailList.add(
                    SongDetail().apply {
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

    fun getSongDetailToMusic(mMusic: Music): SongDetail {
        mMusic.apply {
//            val songDetail = SongDetail()
            return SongDetail().apply {
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
        mSongDet: SongDetail,
        rootPatch: HomePatchDetail
    ): SongDetail {
        mSongDet.apply {
            rootContentId = rootPatch.ContentID
            rootContentType = rootPatch.ContentType
            rootImage = rootPatch.image
        }
        return mSongDet
    }

    fun getFeaturedSongDetailAndRootData(
        mSongDet: FeaturedSongDetail/*,
        rootPatch: HomePatchDetail*/
    ): IMusicModel {
//        val aaa = IMu
//        mSongDet.apply {
//            return IMusicModel().apply {
//                content_Id = content_Id
//                imageUrl = imageUrl
//                titleName = titleName
//                content_Type = content_Type
//                playingUrl = playingUrl
//                artistName = artistName
//                total_duration = total_duration
//                copyright = copyright
//                labelname = labelname
//                releaseDate = releaseDate
//                fav = ""
//                artist_Id = artistId
//                album_Id = albumId
//                rootContentId = ""
//                rootContentType = ""
//                rootImage = ""
//                isPlaying = false
//            }
//        }
        return mSongDet
    }

    fun getArtistContentDataToRootData(
        mSongDet: ArtistContentData,
        rootPatch: HomePatchDetail
    ): ArtistContentData {
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
        mSongTrack: SongTrack,
        rootPatch: HomePatchDetail
    ): SongTrack {
        mSongTrack.apply {
            rootContentId = rootPatch.ContentID
            rootContentType = rootPatch.ContentType
            rootImage = rootPatch.image
        }
        return mSongTrack
    }

    fun getHomePatchDetailToData(data: Data): HomePatchDetail {
        return HomePatchDetail(
            "0",
            "",
            "",
            data.ArtistName,
            data.Id,
            "",
            "",
            data.Id,
            "A",
            "",
            "0",
            data.Follower,
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
            data.Image,
            "",
            ""
        )
    }

    /* fun getHomePatchPodcastEpisodeDetail(data: Track): HomePatchDetail {
         return HomePatchDetail(
             "0",
             "",
             "",
             data.Name,
             data.EpisodeId,
             "",
             "",
             "",
             "",
             "",
             0,
             "",
             false,
             false,
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
             data.ImageUrl,
             "",
             ""
         )
     }*/

/*    fun getHomePatchItemToPodcastEpisode(episode: List<Episode>): HomePatchItem {
        val mPatchDetail = mutableListOf<HomePatchDetail>()
        for (patchItem in episode) {
            mPatchDetail.add(
                HomePatchDetail(
                    "0",
                    "",
                    "",
                    patchItem.Name,
                    patchItem.ShowId,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "0",
                    "",
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
                    patchItem.ImageUrl,
                    "",
                    ""
                )
            )
        }

        return HomePatchItem(
            "",
            "",
            mPatchDetail,
            "",
            "",
            0,
            0
        )
    }*/

    fun getHomePatchItemToData(data: List<Data>): HomePatchItem {
        val mPatchDetail = mutableListOf<HomePatchDetail>()
        for (patchItem in data) {
            mPatchDetail.add(
                HomePatchDetail(
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
        return HomePatchItem(
            "",
            "",
            mPatchDetail,
            "",
            "",
            0,
            0
        )
    }

    fun getHomePatchDetailToAlbumModel(albumModel: ArtistAlbumModelData): HomePatchDetail {
        albumModel.apply {
            return HomePatchDetail(
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

    fun getHomePatchDetailToSongDetail(songDetail: IMusicModel): HomePatchDetail {
        songDetail.apply {
            return HomePatchDetail(
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

    fun getHomePatchDetailToFeaturedPodcastDetails(episode: FeaturedPodcastDetails): HomePatchDetail {
        episode.apply {
            return HomePatchDetail(
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

    /* fun getVideoToSearchData(data: SearchData): Video {
         data.apply {
             return Video(
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
     }*/

//    fun albumSongDetailsNewList(mediaId: String?, aaa: List<IMusicModel>): List<IMusicModel> {
//        val newList: MutableList<IMusicModel> = ArrayList()
//        aaa.forEach {
//            if (it.content_Id == mediaId) {
//                it.isPlaying = true
//                val newItem = it
//                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
//                newList.add(newItem)
//            } else {
//                it.isPlaying = false
//                newList.add(it)
//            }
//        }
//        return newList
//    }

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

//    fun artistArtistContentDataNewList(
//        mediaId: String?,
//        aaa: List<IMusicModel>
//    ): List<IMusicModel> {
//        val newList: MutableList<IMusicModel> = ArrayList()
//        aaa.forEach {
//            if (it.content_Id == mediaId) {
//                it.isPlaying = true
//                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
//                newList.add(it)
//            } else {
//                it.isPlaying = false
//                newList.add(it)
//            }
//        }
//        return newList
//    }
//
//    fun podcastTrackNewList(
//        mediaId: String?,
//        aaa: List<IMusicModel>
//    ): List<IMusicModel> {
//        val newList: MutableList<IMusicModel> = ArrayList()
//        aaa.forEach {
////            if (it.Id.toString() == mediaId) {
//            if (it.content_Id == mediaId) {
//                it.isPlaying = true
//                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
//                newList.add(it)
//            } else {
//                it.isPlaying = false
//                newList.add(it)
//            }
//        }
//        return newList
//    }
//
//    fun featuredSongDetailNewList(
//        mediaId: String?,
//        aaa: List<IMusicModel>
//    ): List<IMusicModel> {
//        val newList: MutableList<IMusicModel> = ArrayList()
//        aaa.forEach {
//            if (it.content_Id == mediaId) {
//                it.isPlaying = true
//                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
//                newList.add(it)
//            } else {
//                it.isPlaying = false
//                newList.add(it)
//            }
//        }
//        return newList
//    }
}