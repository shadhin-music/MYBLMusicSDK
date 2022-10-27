package com.shadhinmusiclibrary.data

interface IMusicModel {
    var mediaId: String?
    var title: String?
    var displayDescription: String?
    var displayIconUrl: String
    var mediaUrl: String
    var artistName: String
    var date: String
    var contentType: String
    var userPlayListId: String

    var episodeId: String
    var starring: String
    var seekable: Boolean
    var details: String
    var totalStream: Long
    var fav: String
    var trackType: String
    var isPaid: Boolean

    var rootId: String
    var rootType: String
    var rootTitle: String
    var rootImage: String

    var isPrepare: Boolean
    var isPlaying: Boolean
}