package com.shadhinmusiclibrary.fragments.music_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R

/**
 * Rezaul Khan
 * https://github.com/rezaulkhan111
 **/
class PodcastFragment : Fragment() {

    private lateinit var includeTrendingPodcastTitle: LinearLayout
    private lateinit var rvTrendingPodcast: RecyclerView

    private lateinit var includeVideoPodcastTitle: LinearLayout
    private lateinit var rvVideoPodcast: RecyclerView

    private lateinit var includeBoothComTitle: LinearLayout
    private lateinit var rvBoothCom: RecyclerView

    private lateinit var includePopularShowTitle: LinearLayout
    private lateinit var rvPopularShow: RecyclerView

    private lateinit var includeMoreEpisodesTitle: LinearLayout
    private lateinit var rvMoreEpisodes: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_podcast, container, false)

        return viewRef;
    }

    private fun initUI(view: View) {
        includeTrendingPodcastTitle = view.findViewById(R.id.include_trending_podcast_title)
        rvTrendingPodcast = view.findViewById(R.id.rv_trending_podcast)

        includeTrendingPodcastTitle = view.findViewById(R.id.include_video_podcast_title)
        rvTrendingPodcast = view.findViewById(R.id.rv_video_podcast)

        includeTrendingPodcastTitle = view.findViewById(R.id.include_booth_com_title)
        rvTrendingPodcast = view.findViewById(R.id.rv_booth_com)

        includeTrendingPodcastTitle = view.findViewById(R.id.include_popular_show_title)
        rvTrendingPodcast = view.findViewById(R.id.rv_popular_show)

        includeTrendingPodcastTitle = view.findViewById(R.id.include_more_episodes_title)
        rvTrendingPodcast = view.findViewById(R.id.rv_more_episodes)
    }
}