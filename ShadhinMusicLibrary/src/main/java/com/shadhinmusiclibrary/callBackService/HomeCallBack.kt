package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.DataDetails

interface HomeCallBack {
    fun onClickItemAndAllItem(dataDetails: DataDetails, listDataDetail: List<DataDetails>)
    fun onClickSeeAll(listDataDetail: List<DataDetails>)
}