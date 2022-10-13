package com.shadhinmusiclibrary.data.model
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class APIResponse<ResultType>(
    val data: ResultType,
    val fav: Any,
    val follow: Any,
    val image: Any,
    val message: String,
    val status: String,
    val total: Int,
    val type: Any
)