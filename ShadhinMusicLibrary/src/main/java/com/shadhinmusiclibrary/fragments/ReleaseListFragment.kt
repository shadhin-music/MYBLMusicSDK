package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ReleaseAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.utils.AppConstantUtils


class ReleaseListFragment : Fragment(),HomeCallBack {
    private lateinit var navController: NavController
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_release_list, container, false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
            //homePatchDetail = it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3)
         recyclerView.adapter =ReleaseAdapter(homePatchItem!!, this)
        val title: TextView = view.findViewById(R.id.tvTitle)
        title.setText(homePatchItem!!.Name)
        val button: AppCompatImageView = view.findViewById(R.id.imageBack)
        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        button.setOnClickListener {
            manager.popBackStack("Top Trending", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(homePatchItem: HomePatchItem) =
            ReleaseListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", homePatchItem)
                }
            }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, patch: HomePatchItem) {

    }

    override fun onClickSeeAll(patch: HomePatchItem) {

    }
}