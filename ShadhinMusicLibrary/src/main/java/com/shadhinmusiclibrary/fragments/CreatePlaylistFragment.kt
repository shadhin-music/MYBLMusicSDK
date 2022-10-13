package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadhinmusiclibrary.R


internal class CreatePlaylistFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_bl_sdk_fragment_create_playlist, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreatePlaylistFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}