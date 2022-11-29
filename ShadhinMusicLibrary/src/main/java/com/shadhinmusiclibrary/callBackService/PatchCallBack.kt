package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.LatestVideoModelDataModel
import com.shadhinmusiclibrary.data.model.PodcastDetailsModel

internal interface PatchCallBack {
    fun onClickItemAndAllItem(itemPosition: Int, selectedData: List<PodcastDetailsModel>)
}