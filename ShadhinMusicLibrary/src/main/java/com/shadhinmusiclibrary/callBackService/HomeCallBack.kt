package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem

interface HomeCallBack {
    fun onClickItemAndAllItem(itemPosition: Int, patch: HomePatchItem)
    fun onClickSeeAll(patch: HomePatchItem)
}