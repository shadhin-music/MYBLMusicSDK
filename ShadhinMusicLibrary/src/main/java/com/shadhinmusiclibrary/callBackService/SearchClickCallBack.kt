package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem

interface SearchClickCallBack {
    fun clickOnSearchBar(selectedHomePatchItem: HomePatchItem )
}