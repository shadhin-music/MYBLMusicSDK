package com.shadhinmusiclibrary.fragments.base

import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.utils.AppConstantUtils


abstract class BaseFragment<V : ViewModel, VMF : ViewModelProvider.Factory> : Fragment(),
    FragmentEntryPoint {
    var viewModel: V? = null
    var argHomePatchItem: HomePatchItem? = null
    var argHomePatchDetail: HomePatchDetail? = null
    lateinit var updatedSongList: MutableList<SongDetail>
    lateinit var playerViewModel: PlayerViewModel
//    var viewModelFactory: VMF? = null

    protected abstract fun getViewModel(): Class<V>

    protected abstract fun getViewModelFactory(): VMF

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argHomePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
            argHomePatchDetail =
                it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
        }
        createPlayerVM()
        viewModel = ViewModelProvider(this, getViewModelFactory())[getViewModel()]
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            requireActivity(),
            injector.playerViewModelFactory
        )[PlayerViewModel::class.java]
    }

    fun playItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        (activity as? SDKMainActivity)?.setMusicPlayerInitData(mSongDetails, clickItemPosition)
    }

    fun playPauseState(playing: Boolean, ivPlayPause: ImageView) {
        if (playing) {
            ivPlayPause.setImageResource(R.drawable.ic_pause_circle_filled)
        } else {
            ivPlayPause.setImageResource(R.drawable.ic_play_linear)
        }
    }
}