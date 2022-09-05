package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem

interface ChildCallback {
    fun onClickItemAndAllItem(itemPosition: Int, homePatchItem: HomePatchItem)
}