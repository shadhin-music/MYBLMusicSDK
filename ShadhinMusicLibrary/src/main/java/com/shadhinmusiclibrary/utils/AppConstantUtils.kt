package com.shadhinmusiclibrary.utils


internal object AppConstantUtils {

    const val requestType = "RequestType"

    // public static final String API_URL = "http://api.shadhin.co/api/";
    var BASE_URL = "https://shadhinmusic.com/api/"
    var BASE_URL_API_shadhinmusic = "https://api.shadhinmusic.com/api/v5/"
    const val LAST_FM_API_URL = "https://ws.audioscrobbler.com/2.0/"

    const val SingleDataItem = "single_data_item"
    const val PatchItem = "patch_item"
    const val PatchDetail = "patch_detail"
    const val SongDetail = "song_detail"
    const val commonData = "data"
    const val SelectedPatchIndex = "selected_patch_index"
    const val LAST_FM_MIN_BIO_CHAR = 100
    const val LAST_FM_API_KEY = "55dd9a0fd0790ee3219022141a8cdf39"
    const val LAST_FM_CONTENT_TYPE_JSON = "json"

    const val DataContentRequestId = "DataContentRequestId"
    const val UI_Request_Type = "UIName"
    const val Requester_Name_Home = "HomeFragment"
    const val Requester_Name_Search = "SearchFragment"
    const val Requester_Name_API = "AllMusic"
}