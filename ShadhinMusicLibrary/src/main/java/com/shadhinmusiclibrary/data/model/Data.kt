package com.shadhinmusiclibrary.data.model

data class Data(
    val Code: String,
    val ContentType: String,
    val Data: List<DataX>,
    val Design: String,
    val Name: String,
    val Sort: Int,
    val Total: Int
)