package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ConcatAdapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*


internal class PodcastDetailsFragment : Fragment() {
// private lateinit var  parentAdapter:ConcatAdapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//
//        return inflater.inflate(R.layout.fragment_podcast_details, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//         setupAdapters()
//    }
//    private fun setupAdapters() {
//        val layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        val config = ConcatAdapter.Config.Builder().apply {
//            setIsolateViewTypes(false)
//        }.build()
//
//        val parentRecycler: RecyclerView = requireView().findViewById(R.id.recyclerView)
//
//        parentAdapter = ConcatAdapter(
//           // config,PodcastHeaderAdapter(argHomePatchDetail),HeaderAdapter(),PodcastEpisodesAdapter(),HeaderAdapter(),PodcastMoreEpisodesAdapter(),PodcastCommentAdapter()
//
//        )
//        parentRecycler.setLayoutManager(layoutManager)
//        parentRecycler.setAdapter(parentAdapter)
//        val back: ImageView? = view?.findViewById(R.id.imageBack)
//
//        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//        back?.setOnClickListener {
//            manager.popBackStack("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
//
//        }
//    }
//    companion object {
//
//
//        @JvmStatic
//        fun newInstance() =
//            PodcastDetailsFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }
}