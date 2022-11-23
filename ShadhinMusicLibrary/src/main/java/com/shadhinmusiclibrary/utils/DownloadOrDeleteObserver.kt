package com.shadhinmusiclibrary.utils

import com.shadhinmusiclibrary.fragments.DownloadOrDeleteActionSubscriber

internal object DownloadOrDeleteObserver {

    private var subscriber: DownloadOrDeleteActionSubscriber? = null

    fun addSubscriber(subscriber: DownloadOrDeleteActionSubscriber) {
        this.subscriber = subscriber
    }

    fun removeSubscriber() {
        this.subscriber = null
    }

    fun notifySubscriber() {
        this.subscriber?.notifyOnChange()
    }
}