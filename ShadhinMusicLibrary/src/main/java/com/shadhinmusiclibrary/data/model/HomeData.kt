package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
internal class HomeData {
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var homePatchItems: MutableList<HomePatchItem?>? = null

    @SerializedName("total")
    @Expose
    private var total: Int? = null

    @SerializedName("type")
    @Expose
    private var type: Any? = null

    @SerializedName("fav")
    @Expose
    private var fav: Any? = null

    @SerializedName("image")
    @Expose
    private var image: Any? = null

    @SerializedName("follow")
    @Expose
    private var follow: Any? = null

    @SerializedName("MonthlyListener")
    @Expose
    private var monthlyListener: Any? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getData(): MutableList<HomePatchItem?>? {
        return homePatchItems
    }

    fun setData(homePatchItems: MutableList<HomePatchItem?>?) {
        this.homePatchItems = homePatchItems
    }

    fun getTotal(): Int? {
        return total
    }

    fun setTotal(total: Int) {
        this.total = total
    }

    fun getType(): Any? {
        return type
    }

    fun setType(type: Any) {
        this.type = type
    }

    fun getFav(): Any? {
        return fav
    }

    fun setFav(fav: Any) {
        this.fav = fav
    }

    fun getImage(): Any? {
        return image
    }

    fun setImage(image: Any) {
        this.image = image
    }

    fun getFollow(): Any? {
        return follow
    }

    fun setFollow(follow: Any) {
        this.follow = follow
    }

    fun getMonthlyListener(): Any? {
        return monthlyListener
    }

    fun setMonthlyListener(monthlyListener: Any?) {
        this.monthlyListener = monthlyListener
    }
}