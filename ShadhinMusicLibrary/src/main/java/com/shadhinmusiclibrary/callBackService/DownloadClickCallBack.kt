package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem

internal interface DownloadClickCallBack {
    fun clickOnDownload(selectedHomePatchItem: HomePatchItem)
}