package com.shadhinmusiclibrary.fragments.base

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.utils.AppConstantUtils

open class CommonBaseFragment : Fragment() {
    var argHomePatchItem: HomePatchItem? = null
    var argHomePatchDetail: HomePatchDetail? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argHomePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
            argHomePatchDetail =
                it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?

        }
    }

    fun playItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        (activity as? SDKMainActivity)?.setMusicPlayerInitData(mSongDetails, clickItemPosition)
    }
}