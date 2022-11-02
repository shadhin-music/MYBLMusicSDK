package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItemModel

internal interface DownloadClickCallBack {
    fun clickOnDownload(selectedHomePatchItem: HomePatchItemModel)
    fun clickOnWatchlater(selectedHomePatchItem: HomePatchItemModel)
    fun clickOnMyPlaylist(selectedHomePatchItem: HomePatchItemModel)
}