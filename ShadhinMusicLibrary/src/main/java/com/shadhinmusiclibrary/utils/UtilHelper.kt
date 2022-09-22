package com.shadhinmusiclibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music

object UtilHelper {
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
                        ContentID = ShowId,
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
                        rootType = ContentType,

                        rootContentID = ShowId,
                        rootContentType = ContentType,
                        rootImage = ImageUrl
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
                        rootType = ContentType,

                        rootContentID = ContentID,
                        rootContentType = ContentType,
                        rootImage = image
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
                        ContentID = mediaId!!,
                        image = displayIconUrl!!,
                        title = title!!,
                        ContentType = if (contentType != null) {
                            contentType!!
                        } else {
                            ""
                        },
                        PlayUrl = mediaUrl!!,
                        artist = artistName!!,
                        duration = date!!,
                        copyright = "",
                        labelname = "",
                        releaseDate = "",
                        fav = "",

                        ArtistId = "",
                        albumId = "",
                        userPlayListId = if (userPlayListId != null) {
                            userPlayListId!!
                        } else {
                            ""
                        },
                        rootType = rootType!!,

                        rootContentID = rootId!!,
                        rootContentType = rootType!!,
                        rootImage = rootImage!!
                    )
                )
            }
        }

        return songDetailList
    }

    fun getSongDetailToMusic(mMusic: Music): SongDetail {
        mMusic.apply {
            return SongDetail(
                ContentID = mediaId!!,
                image = displayIconUrl!!,
                title = title!!,
                ContentType = if (contentType != null) {
                    contentType!!
                } else {
                    ""
                },
                PlayUrl = mediaUrl!!,
                artist = artistName!!,
                duration = date!!,
                copyright = "",
                labelname = "",
                releaseDate = "",
                fav = "",

                ArtistId = "",
                albumId = "",
                userPlayListId = if (userPlayListId != null) {
                    userPlayListId!!
                } else {
                    ""
                },
                rootType = rootType!!,

                rootContentID = rootId!!,
                rootContentType = rootType!!,
                rootImage = rootImage!!
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
                rootType = rootPatch.ContentType,

                rootContentID = rootPatch.ContentID,
                rootContentType = rootPatch.ContentType,
                rootImage = rootPatch.image
            )
        }
    }
}