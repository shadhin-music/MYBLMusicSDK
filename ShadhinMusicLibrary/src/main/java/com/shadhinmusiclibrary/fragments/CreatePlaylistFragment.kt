package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadhinmusiclibrary.R


internal class CreatePlaylistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_create_playlist, container, false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_bl_sdk_fragment_create_playlist, container, false)
    }
}