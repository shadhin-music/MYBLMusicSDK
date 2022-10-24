package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.DownloadedSongsAdapter
import com.shadhinmusiclibrary.player.utils.CacheRepository


internal class DownloadDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.my_bl_sdk_fragment_download_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      loadData()


    }
      fun loadData(){
          val cacheRepository= CacheRepository(requireContext())
          val dataAdapter = DownloadedSongsAdapter(cacheRepository.getAllDownloads()!!)

          val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
          recyclerView.layoutManager =
              LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
          recyclerView.adapter = dataAdapter

      }

    companion object {

        @JvmStatic
        fun newInstance() =
            DownloadDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}
interface DownloadItemUpdateListener {
    fun loadData()
}