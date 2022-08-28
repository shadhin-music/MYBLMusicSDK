package com.shadhinmusiclibrary.data.model

import android.provider.ContactsContract
import androidx.annotation.ColorInt


sealed class GenreDataModel {

    data class Artist(
        val name: String
    ) : GenreDataModel()

    data class Artist2(
        val name: String
    ) : GenreDataModel()
    data class Artist3(
        val name: String
    ) : GenreDataModel()
    data class Artist4(
        val name: String
    ) : GenreDataModel()
}
