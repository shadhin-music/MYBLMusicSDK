package com.shadhinmusiclibrary.data.model

data class SortDescription(
    val Code: String,
    val ContentType: String,
    val Data: List<DataDetails>,
    val Design: String,
    val Name: String,
    val Sort: Int,
    val Total: Int
)