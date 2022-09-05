package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.PopularArtistAdapter
import com.shadhinmusiclibrary.callBackService.ChildCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable


class PopularArtistsFragment : Fragment(), HomeCallBack {
    var homePatchItem: HomePatchItem? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewR = inflater.inflate(R.layout.fragment_popular_artists, container, false)
        navController = findNavController()

        return viewR;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homePatchItem =
            arguments?.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 4)
        recyclerView.adapter = homePatchItem.let { PopularArtistAdapter(it, this) }
        val back: ImageView = requireView().findViewById(R.id.imageBack)
//        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        back.setOnClickListener {
//            manager.popBackStack("Popular Artist", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        navController.navigate(
            R.id.action_PopularArtistFragment_to_ArtistDetailsFragment,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    homePatchItem as Serializable
                )
            })
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {

    }
}