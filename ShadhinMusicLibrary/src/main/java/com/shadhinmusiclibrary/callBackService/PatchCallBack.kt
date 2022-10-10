package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.Data

interface PatchCallBack {
    fun onClickItemAndAllItem(itemPosition: Int, selectedData: List<Data>)
//    fun onClickSeeAll(selectedHomePatchItem: HomePatchItem)
}