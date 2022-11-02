package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem

internal interface DownloadClickCallBack {
    fun clickOnDownload(selectedHomePatchItem: HomePatchItem)
    fun clickOnWatchlater(selectedHomePatchItem: HomePatchItem)
    fun clickOnMyPlaylist(selectedHomePatchItem: HomePatchItem)
}