package com.shadhinmusiclibrary.data.model

data class APIResponse<ResultType>(
    val data: ResultType,
    val fav: Any,
    val follow: Any,
    val image: Any,
    val message: String,
    val status: String,
    val total: Int,
    val type: Any
)