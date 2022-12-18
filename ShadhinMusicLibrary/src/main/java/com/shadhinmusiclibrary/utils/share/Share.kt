package com.shadhinmusiclibrary.utils.share

interface Share {
    val code:String
    val contentId:String?
    val contentType:String?
    val isPodcast:Boolean
    val podcastSubType:String?
}