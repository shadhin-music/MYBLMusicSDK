package com.shadhinmusiclibrary.bottom_sheet

import android.R.attr.bitmap
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.callBackService.ChildCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.library.discretescrollview.DSVOrientation
import com.shadhinmusiclibrary.library.discretescrollview.DiscreteScrollView
import java.io.IOException
import java.net.URL


class MusicPlayBS : Fragment(),
    DiscreteScrollView.OnItemChangedListener<MusicPlayAdapter.MusicPlayVH>,
    DiscreteScrollView.ScrollStateChangeListener<MusicPlayAdapter.MusicPlayVH>,
    ChildCallback {

    private lateinit var clMainMusicPlayerParent: ConstraintLayout
    private lateinit var acivMinimizePlayerBtn: AppCompatImageView
    private lateinit var tvTitle: TextView
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

    private lateinit var listData: MutableList<HomePatchDetail>

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
        clMainMusicPlayerParent = viewRef.findViewById(R.id.cl_main_music_player_parent)
        acivMinimizePlayerBtn = viewRef.findViewById(R.id.aciv_minimize_player_btn)
        tvTitle = viewRef.findViewById(R.id.tv_title)
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
        val adapter = MusicPlayAdapter(requireContext(), this)
        listData = mutableListOf()
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
                    "https://shadhinmusiccontent.sgp1.digitaloceanspaces.com/AlbumPreviewImageFile/PremTumiKi_AyubBachchu_300.jpg",
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

        dsvCurrentPlaySongsThumb.addScrollStateChangeListener(this)
        dsvCurrentPlaySongsThumb.addOnItemChangedListener(this)
    }

    private fun uiFunctionality() {
        ibtnShuffle.setOnClickListener {
            setControlColor(ibtnShuffle)
//            R.color.vector_image_control.set
        }

        ibtnSkipPrevious.setOnClickListener {

        }

        ibtnPlayPause.setOnClickListener {
            setControlColor(ibtnPlayPause)
        }

        ibtnSkipNext.setOnClickListener { }

        ibtnRepeatSong.setOnClickListener {
            setControlColor(ibtnRepeatSong)
        }

        ibtnVolume.setOnClickListener {
            setControlColor(ibtnVolume)
        }

        ibtnLibraryAdd.setOnClickListener {
            setControlColor(ibtnLibraryAdd)
        }

        ibtnQueueMusic.setOnClickListener {
            setControlColor(ibtnQueueMusic)
        }

        ibtnDownload.setOnClickListener {
            setControlColor(ibtnDownload)
        }
    }

    private fun setControlColor(ibtnControl: ImageButton) {
        ibtnControl.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            ), PorterDuff.Mode.SRC_IN
        )
    }

    override fun onCurrentItemChanged(
        viewHolder: MusicPlayAdapter.MusicPlayVH?,
        adapterPosition: Int
    ) {
        if (viewHolder != null) {
            setPaletteGrdientColor(getBitmapFromVH(viewHolder))
        }
    }

    override fun onScrollStart(
        currentItemHolder: MusicPlayAdapter.MusicPlayVH,
        adapterPosition: Int
    ) {
    }

    override fun onScrollEnd(
        currentItemHolder: MusicPlayAdapter.MusicPlayVH,
        adapterPosition: Int
    ) {
    }

    override fun onScroll(
        scrollPosition: Float,
        currentPosition: Int,
        newPosition: Int,
        currentHolder: MusicPlayAdapter.MusicPlayVH?,
        newCurrent: MusicPlayAdapter.MusicPlayVH?
    ) {
        if (currentHolder != null) {
            setPaletteGrdientColor(getBitmapFromVH(currentHolder))
        }
    }

    private fun setPaletteGrdientColor(imBitmapData: Bitmap) {
        val palette: Palette = Palette.from(imBitmapData).generate()
        val vibrant: Swatch = palette.vibrantSwatch!!

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.shadinRequiredColor),
                vibrant.rgb
            )
        );
        gradientDrawable.cornerRadius = 0f;
//        clMainMusicPlayerParent.setBackgroundColor(vibrant.rgb)
        clMainMusicPlayerParent.background = gradientDrawable
    }

    private fun getBitmapFromVH(currentItemHolder: MusicPlayAdapter.MusicPlayVH): Bitmap {
        val imageV = currentItemHolder.ivCurrentPlayImage
        val traDaw = imageV.drawable
        return (traDaw.toBitmap())
    }

    override fun onClickItemAndAllItem(currentBitmap: Bitmap) {
        Log.e("MPBS", "onClickItemAndAllItem: $currentBitmap")
    }
}