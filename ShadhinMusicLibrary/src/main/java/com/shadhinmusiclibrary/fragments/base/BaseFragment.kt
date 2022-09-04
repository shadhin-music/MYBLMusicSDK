package com.shadhinmusiclibrary.fragments.base

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.utils.AppConstantUtils


abstract class BaseFragment<V : ViewModel, VMF : ViewModelProvider.Factory> : Fragment() {
    var viewModel: V? = null
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null
//    var viewModelFactory: VMF? = null

    protected abstract fun getViewModel(): Class<V>

    protected abstract fun getViewModelFactory(): VMF

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
            homePatchDetail = it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
        }

        viewModel = ViewModelProvider(this, getViewModelFactory())[getViewModel()]
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
    }
}