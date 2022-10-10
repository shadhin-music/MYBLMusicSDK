package com.shadhinmusiclibrary.data.model

data class LatestVideoModel(
    val `data`: List<LatestVideoModelData>,
    val message: String,
    val status: String
)