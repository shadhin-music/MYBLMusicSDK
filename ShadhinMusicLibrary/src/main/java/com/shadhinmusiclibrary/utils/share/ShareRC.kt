package com.shadhinmusiclibrary.utils.share

import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.utils.fromBase64
import com.shadhinmusiclibrary.utils.toBase64

class ShareRC(override val code: String) : Share {
    private val token: List<String>? by lazy { decodeToken() }

    override val contentId: String?
        get() = token?.first()
    override val contentType: String?
        get() = token?.last()

    override val isPodcast: Boolean
        get() = contentType?.contains("PD", true) ?: false

    override val podcastSubType: String?
        get() = if (isPodcast && (contentType?.length ?: 0) >= 4)
            contentType?.substring(2, (contentType?.length ?: 0))
        else null

    private fun decodeToken(): List<String>? {
        val str = code.fromBase64()
        val tokens = str.split("_")
        if (tokens.size == 2) {
            return tokens
        }
        return null
    }

    override fun toString(): String {
        return "ShareRC(code='$code', contentId=$contentId, contentType=$contentType, isPodcast=$isPodcast, podcastSubType=$podcastSubType)"
    }


    companion object {
        @JvmStatic
        fun generate(iMusicModel: IMusicModel): ShareRC {
            return generate(iMusicModel.content_Id, iMusicModel.content_Type ?: "")
        }

        @JvmStatic
        fun generate(contentId: String?, contentType: String?): ShareRC {
            val token = "${contentId ?: ""}_${contentType ?: ""}".toBase64()
            return ShareRC(token)
        }
    }

}

