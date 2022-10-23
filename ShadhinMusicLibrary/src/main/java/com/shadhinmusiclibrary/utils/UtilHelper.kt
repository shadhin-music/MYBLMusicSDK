package com.shadhinmusiclibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.data.model.search.SearchData
import com.shadhinmusiclibrary.data.model.search.TopTrendingdata
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music

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

    fun getMusicToSongDetail(mSongDet: SongDetail): Music {
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
    }

    fun getMusicListToSongDetailList(mSongDetails: MutableList<SongDetail>): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        for (songItem in mSongDetails) {
            songItem.apply {
                musicList.add(
                    Music(
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
                )
            }
        }

        return musicList
    }

    fun getSongDetailToTrackList(trackList: MutableList<Track>): MutableList<SongDetail> {
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
                        /*rootType = ContentType,*/

                        rootContentID = rootContentID,
                        rootContentType = rootContentType,
                        rootImage = rootImage
                    )
                )
            }
        }
        return songDetailList
    }

    fun getSongDetailToTopTrendingDataList(topTrandList: List<TopTrendingdata>): MutableList<SongDetail> {
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
                        /*rootType = ContentType,*/

                        rootContentID = ContentID,
                        rootContentType = ContentType,
                        rootImage = image
                    )
                )
            }
        }
        return songDetailList
    }

    fun getSongDetailToSearchDataList(topTrandList: List<SearchData>): MutableList<SongDetail> {
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
                        /*rootType = ContentType,*/

                        /*  rootContentID = trackItem.rootContentID,
                          rootContentType = trackItem.rootContentType,
                          rootImage = trackItem.rootImage*/
                        rootContentID = ContentID,
                        rootContentType = ContentType,
                        rootImage = image
                    )
                )
            }
        }
        return songDetailList
    }

    fun getSongDetailToFeaturedSongDetailList(trackList: MutableList<FeaturedSongDetail>): MutableList<SongDetail> {
        val songDetailList = mutableListOf<SongDetail>()
        for (trackItem in trackList) {
            trackItem.apply {
                songDetailList.add(
                    SongDetail(
                        ContentID = contentID,
                        image = image,
                        title = title,
                        ContentType = contentType,
                        PlayUrl = playUrl,
                        artist = artistname,
                        duration = duration,
                        copyright = copyright,
                        labelname = labelname,
                        releaseDate = releaseDate,
                        fav = "",
                        ArtistId = artistId,
                        albumId = albumId,
                        userPlayListId = "",
                        /*rootType = contentType,*/

//                        rootContentID = rootContentID,
//                        rootContentType = rootContentType,
//                        rootImage = rootImage
                        rootContentID = contentID ?: "00007",
                        rootContentType = contentType ?: "R",
                        rootImage = image ?: ""
                    )
                )
            }
        }
        return songDetailList
    }

    fun getSongDetailToArtistContentDataList(musicList: MutableList<ArtistContentData>): MutableList<SongDetail> {
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
                        /*rootType = rootContentType,*/

                        rootContentID = rootContentID,
                        rootContentType = rootContentType,
                        rootImage = rootImage
                    )
                )
            }
        }

        return songDetailList
    }

    fun getSongDetailToMusicList(musicList: MutableList<Music>): MutableList<SongDetail> {
        val songDetailList = mutableListOf<SongDetail>()
        for (musicItem in musicList) {
            musicItem.apply {
                songDetailList.add(
                    SongDetail(
                        ContentID = mediaId ?: "",
                        image = displayIconUrl ?: "",
                        title = title ?: "",
                        ContentType = contentType ?: "",
                        PlayUrl = mediaUrl ?: "",
                        artist = artistName ?: "",
                        duration = date ?: "",
                        copyright = "",
                        labelname = "",
                        releaseDate = "",
                        fav = "",

                        ArtistId = "",
                        albumId = "",
                        userPlayListId = userPlayListId ?: "",
                        //    rootType = rootType?:"",

                        rootContentID = rootId ?: "",
                        rootContentType = rootType ?: "",
                        rootImage = rootImage ?: ""
                    )
                )
            }
        }

        return songDetailList
    }

    fun getSongDetailToMusic(mMusic: Music): SongDetail {
        mMusic.apply {
            return SongDetail(
                ContentID = mediaId ?: "",
                image = displayIconUrl ?: "",
                title = title ?: "",
                ContentType = contentType ?: "",
                PlayUrl = mediaUrl ?: "",
                artist = artistName ?: "",
                duration = date ?: "",
                copyright = "",
                labelname = "",
                releaseDate = "",
                fav = "",

                ArtistId = "",
                albumId = "",
                userPlayListId = userPlayListId ?: "",
                //rootType = rootType?:"",

                rootContentID = rootId ?: "",
                rootContentType = rootType ?: "",
                rootImage = rootImage ?: ""
            )
        }
    }

    fun getSongDetailAndRootData(
        mSongDet: SongDetail,
        rootPatch: HomePatchDetail
    ): SongDetail {
        mSongDet.apply {
            return SongDetail(
                ContentID = ContentID,
                image = image,
                title = title,
                ContentType = ContentType,
                PlayUrl = PlayUrl,
                artist = artist,
                duration = duration,
                copyright = copyright,
                labelname = labelname,
                releaseDate = releaseDate,
                fav = fav,
                ArtistId = ArtistId,
                albumId = albumId,
                userPlayListId = userPlayListId,
                /*rootType = rootPatch.ContentType,*/

                rootContentID = rootPatch.ContentID,
                rootContentType = rootPatch.ContentType,
                rootImage = rootPatch.image
            )
        }
    }

    fun getFeaturedSongDetailAndRootData(
        mSongDet: FeaturedSongDetail/*,
        rootPatch: HomePatchDetail*/
    ): FeaturedSongDetail {
        mSongDet.apply {
            return FeaturedSongDetail(
                contentID = contentID,
                image = image,
                title = title,
                contentType = contentType,
                playUrl = playUrl,
                artistname = artistname,
                duration = duration,
                copyright = copyright,
                labelname = labelname,
                releaseDate = releaseDate,
                fav = "",
                artistId = artistId,
                albumId = albumId,
                /*rootType = rootPatch.ContentType,*/

                rootContentID = "",
                rootContentType = "",
                rootImage = "",
                isPlaying = false
            )
        }
    }

    fun getArtistContentDataToRootData(
        mSongDet: ArtistContentData,
        rootPatch: HomePatchDetail
    ): ArtistContentData {
        mSongDet.apply {
            return ArtistContentData(
                ContentID = ContentID,
                image = image,
                title = title,
                ContentType = ContentType,
                TotalPlay = 0,
                PlayUrl = PlayUrl,
                artistname = artistname,
                duration = duration,
                copyright = copyright,
                labelname = labelname,
                releaseDate = releaseDate,
                fav = fav,
                ArtistId = ArtistId,
                AlbumId = AlbumId,
                rootContentID = rootPatch.ContentID,
                rootContentType = rootPatch.ContentType,
                rootImage = rootPatch.image
            )
        }
    }

    fun getTrackToRootData(
        mTrack: Track,
        rootPatch: HomePatchDetail
    ): Track {
        mTrack.apply {
            return Track(
                ShowId = ShowId,
                ImageUrl = ImageUrl,
                Name = Name,
                ContentType = ContentType,
                PlayUrl = PlayUrl,
                Starring = Starring,
                Duration = Duration,
                fav = fav,
                IsPaid = IsPaid,
                TrackType = TrackType,
                CeateDate = CeateDate,
                Details = Details,
                EpisodeId = EpisodeId,
                Id = Id,
                Seekable = Seekable,
                Sort = Sort,
                totalStream = totalStream,
                rootContentID = rootPatch.ContentID,
                rootContentType = rootPatch.ContentType,
                rootImage = rootPatch.image,
            )
        }
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
            "",
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

    fun getHomePatchPodcastEpisodeDetail(data: Track): HomePatchDetail {
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
            "",
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
            data.ImageUrl,
            "",
            ""
        )
    }

    fun getHomePatchItemToPodcastEpisode(episode: List<Episode>): HomePatchItem {
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
                    "",
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
    }

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
                    "",
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
                AlbumId = AlbumId,
                ArtistId = ArtistId,
                ContentID = ContentID,
                ContentType = ContentType,
                PlayUrl = PlayUrl,
                AlbumName = title,
                AlbumImage = "",
                fav = fav,
                Banner = "",
                Duration = duration,
                TrackType = "",
                image = image,
                ArtistImage = "",
                Artist = artistname,
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

    fun getVideoToSearchData(data: SearchData): Video {
        data.apply {
            return Video(
                albumId = AlbumId,
                albumImage = AlbumImage,
                albumName = AlbumName,
                artist = Artist,
                artistId = AlbumId,
                artistImage = ArtistImage,
                banner = Banner,
                contentID = ContentID,
                contentType = ContentType,
                createDate = CreateDate,
                duration = Duration,
                follower = Follower,
                isPaid = IsPaid,
                newBanner = NewBanner,
                playCount = PlayCount,
                playListId = PlayListId,
                playListImage = PlayListImage,
                playListName = PlayListName,
                playUrl = PlayUrl,
                rootId = RootId,
                rootType = Type,
                seekable = Seekable,
                teaserUrl = TeaserUrl,
                trackType = TrackType,
                type = Type,
                fav = fav,
                image = image,
                imageWeb = imageWeb,
                title = title
            )
        }
    }

    fun albumSongDetailsNewList(mediaId: String?, aaa: List<SongDetail>): List<SongDetail> {
        val newList: MutableList<SongDetail> = ArrayList()
        aaa.forEach {
            if (it.ContentID == mediaId) {
                val newItem = it.copy(isPlaying = true)
                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
                newList.add(newItem)
            } else {
                newList.add(it.copy(isPlaying = false))
            }
        }
        return newList
    }

    fun artistArtistContentDataNewList(
        mediaId: String?,
        aaa: List<ArtistContentData>
    ): List<ArtistContentData> {
        val newList: MutableList<ArtistContentData> = ArrayList()
        aaa.forEach {
            if (it.ContentID == mediaId) {
                val newItem = it.copy(isPlaying = true)
                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
                newList.add(newItem)
            } else {
                newList.add(it.copy(isPlaying = false))
            }
        }
        return newList
    }

    fun podcastTrackNewList(
        mediaId: String?,
        aaa: List<Track>
    ): List<Track> {
        val newList: MutableList<Track> = ArrayList()
        aaa.forEach {
            if (it.Id.toString() == mediaId) {
                val newItem = it.copy(isPlaying = true)
                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
                newList.add(newItem)
            } else {
                newList.add(it.copy(isPlaying = false))
            }
        }
        return newList
    }

    fun featuredSongDetailNewList(
        mediaId: String?,
        aaa: List<FeaturedSongDetail>
    ): List<FeaturedSongDetail> {
        val newList: MutableList<FeaturedSongDetail> = ArrayList()
        aaa.forEach {
            if (it.contentID == mediaId) {
                val newItem = it.copy(isPlaying = true)
                //   newItem.isPlaying = it.ContentID == mediaId || !it.isPlaying
                newList.add(newItem)
            } else {
                newList.add(it.copy(isPlaying = false))
            }
        }
        return newList
    }
}