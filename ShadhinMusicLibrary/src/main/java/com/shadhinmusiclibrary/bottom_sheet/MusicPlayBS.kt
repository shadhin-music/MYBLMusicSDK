package com.shadhinmusiclibrary.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.library.discretescrollview.DSVOrientation
import com.shadhinmusiclibrary.library.discretescrollview.DiscreteScrollView

class MusicPlayBS : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_music_player2, container, false)
        val dsvCurrentPlaySongsThumb: DiscreteScrollView =
            view.findViewById(R.id.dsv_current_play_songs_thumb)
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
        return view
    }
}