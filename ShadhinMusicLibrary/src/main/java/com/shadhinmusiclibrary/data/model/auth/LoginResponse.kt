package com.shadhinmusiclibrary.data.model.auth


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginResponse(
    @SerializedName("data")
    var `data`: Data? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: String? = null
) {
    @Keep
    data class Data(
        @SerializedName("fullName")
        var fullName: Any? = null,
        @SerializedName("gender")
        var gender: Any? = null,
        @SerializedName("msisdn")
        var msisdn: String? = null,
        @SerializedName("Token")
        var token: String? = null,
        @SerializedName("userPic")
        var userPic: Any? = null
    )
}