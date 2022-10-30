package com.shadhinmusiclibrary.fragments.base

import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.library.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.utils.AppConstantUtils


internal abstract class BaseFragment<V : ViewModel, VMF : ViewModelProvider.Factory> : Fragment(),
    FragmentEntryPoint {
    var viewModel: V? = null

    // var viewModel2: V? = null
    var argHomePatchItem: HomePatchItemModel? = null
    var argHomePatchDetail: HomePatchDetailModel? = null
    lateinit var updatedSongList: MutableList<SongDetailModel>
    lateinit var playerViewModel: PlayerViewModel
//    var viewModelFactory: VMF? = null

    protected abstract fun getViewModel(): Class<V>

    protected abstract fun getViewModelFactory(): VMF

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argHomePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItemModel?
            argHomePatchDetail =
                it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetailModel?
        }
        createPlayerVM()
        viewModel = ViewModelProvider(this, getViewModelFactory())[getViewModel()]
       // viewModel2 = ViewModelProvider(this, getViewModelFactory())[getViewModel()]
        //viewModel = ViewModelProvider(this, get).get(getViewModel())
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            requireActivity(),
            injector.playerViewModelFactory
        )[PlayerViewModel::class.java]

    }

    fun playItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        (activity as? SDKMainActivity)?.setMusicPlayerInitData(mSongDetails, clickItemPosition)
    }

    fun playPauseState(playing: Boolean, ivPlayPause: ImageView) {
        if (playing) {
            ivPlayPause.setImageResource(R.drawable.my_bl_sdk_ic_pause_circle_filled)
        } else {
            ivPlayPause.setImageResource(R.drawable.my_bl_sdk_ic_play_linear)
        }
    }
}