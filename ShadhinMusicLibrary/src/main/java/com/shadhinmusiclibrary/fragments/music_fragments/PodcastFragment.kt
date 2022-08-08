package com.shadhinmusiclibrary.fragments.music_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadhinmusiclibrary.R
/**
 * Rezaul Khan
 * https://github.com/rezaulkhan111
 **/
class PodcastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_podcast, container, false)
    }
}