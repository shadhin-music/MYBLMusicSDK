package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem

internal interface SearchClickCallBack {
    fun clickOnSearchBar(selectedHomePatchItem: HomePatchItem )
}