package com.shadhinmusiclibrary.bottom_sheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.library.discretescrollview.DSVOrientation
import com.shadhinmusiclibrary.library.discretescrollview.DiscreteScrollView

class MusicPlayBS : Fragment() {

    private lateinit var acivMinimizePlayerBtn: AppCompatImageView
    private lateinit var tvTitle: AppCompatImageView
    private lateinit var acivMenu: AppCompatImageView
    private lateinit var dsvCurrentPlaySongsThumb: DiscreteScrollView
    private lateinit var tvSongName: TextView
    private lateinit var tvSingerName: TextView
    private lateinit var ivFavoriteBtn: ImageView
    private lateinit var sbCurrentPlaySongStatus: SeekBar
    private lateinit var tvCurrentPlayDuration: TextView
    private lateinit var tvTotalPlayDuration: TextView
    private lateinit var ibtnShuffle: ImageButton
    private lateinit var ibtnSkipPrevious: ImageButton
    private lateinit var ibtnPlayPause: ImageButton
    private lateinit var ibtnSkipNext: ImageButton
    private lateinit var ibtnRepeatSong: ImageButton
    private lateinit var ibtnVolume: ImageButton
    private lateinit var ibtnLibraryAdd: ImageButton
    private lateinit var ibtnQueueMusic: ImageButton
    private lateinit var ibtnDownload: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_music_player2, container, false)
        initUI(view)

        setMusicBannerAdapter()
        uiFunctionality()

        return view
    }

    private fun initUI(viewRef: View) {
        acivMinimizePlayerBtn = viewRef.findViewById(R.id.aciv_minimize_player_btn)
        tvTitle = viewRef.findViewById(R.id.tvTitle)
        acivMenu = viewRef.findViewById(R.id.aciv_menu)
        dsvCurrentPlaySongsThumb = viewRef.findViewById(R.id.dsv_current_play_songs_thumb)
        tvSongName = viewRef.findViewById(R.id.tv_song_name)
        tvSingerName = viewRef.findViewById(R.id.tv_singer_name)
        ivFavoriteBtn = viewRef.findViewById(R.id.iv_favorite_btn)
        sbCurrentPlaySongStatus = viewRef.findViewById(R.id.sb_current_play_song_status)
        tvCurrentPlayDuration = viewRef.findViewById(R.id.tv_current_play_duration)
        tvTotalPlayDuration = viewRef.findViewById(R.id.tv_total_play_duration)
        ibtnShuffle = viewRef.findViewById(R.id.ibtn_shuffle)
        ibtnSkipPrevious = viewRef.findViewById(R.id.ibtn_skip_previous)
        ibtnPlayPause = viewRef.findViewById(R.id.ibtn_play_pause)
        ibtnSkipNext = viewRef.findViewById(R.id.ibtn_skip_next)
        ibtnRepeatSong = viewRef.findViewById(R.id.ibtn_repeat_song)
        ibtnVolume = viewRef.findViewById(R.id.ibtn_volume)
        ibtnLibraryAdd = viewRef.findViewById(R.id.ibtn_library_add)
        ibtnQueueMusic = viewRef.findViewById(R.id.ibtn_queue_music)
        ibtnDownload = viewRef.findViewById(R.id.ibtn_download)
    }

    private fun setMusicBannerAdapter() {
        val adapter = MusicPlayAdapter()
        val listData = mutableListOf<HomePatchDetail>()
        for (i in 1..3) {
            listData.add(
                HomePatchDetail(
                    "",
                    "",
                    "",
                    "",
                    "",
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
                    "https://s3-alpha-sig.figma.com/img/c1e6/aa8d/9b7ead647d0f62b68e0aea77f737cab7?Expires=1663545600&Signature=EstkpW5-lhvXiOaA6kQ7Sr-1u-MsXUlvrTVGUywsoi4bVVt-9h3KnFqiUNvnX40IhPv9HEg7Ff9iIy7DA53n865d~ZA~~XO5uwAlC2hW3omZ6iFL-a05wCB1NR-1LOcRbacDtv-tUojhpy~VfA1JYN49aDLRw5g8agQJKhlBve5CvWLlOhnFoah~WJhXmbBOGqCb44uuFbfKVSDHvffTqJFFdulFjDIUuwZ7YPAIWb9nDE0xhS9TsQqweycuq8IRS0GKgM2E5nRgLCvVUyy-mXc4ECrvrGpCC1GzyQZVKzTKxMHuh6IMl83VefTt4EY~CqsGjeAUUnmUQ2eiD~lk8g__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
                    "",
                    ""
                )
            )
        }
        adapter.setMusicData(listData)

        dsvCurrentPlaySongsThumb.adapter = adapter
        dsvCurrentPlaySongsThumb.setOrientation(DSVOrientation.HORIZONTAL)
        dsvCurrentPlaySongsThumb.setSlideOnFling(true)
        dsvCurrentPlaySongsThumb.setOffscreenItems(2)
        dsvCurrentPlaySongsThumb.setItemTransitionTimeMillis(150)
        dsvCurrentPlaySongsThumb.itemAnimator = null
    }

    private fun uiFunctionality() {
        ibtnShuffle.setOnClickListener { }

        ibtnSkipPrevious.setOnClickListener { }

        ibtnPlayPause.setOnClickListener { }

        ibtnSkipNext.setOnClickListener { }

        ibtnRepeatSong.setOnClickListener { }

        ibtnVolume.setOnClickListener { }

        ibtnLibraryAdd.setOnClickListener { }

        ibtnQueueMusic.setOnClickListener { }

        ibtnDownload.setOnClickListener { }
    }

    private fun aaa(resId: Int) {
        var d: Drawable = VectorDrawableCompat.create(resources, resId, null)!!
        d = DrawableCompat.wrap(d);
//        DrawableCompat.setTint(d, headerTitleColor);
    }
}