package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Keep
internal class HomePatchItem {
    @SerializedName("Sort")
    @Expose
    private var sort: Int? = null

    @SerializedName("Total")
    @Expose
    private var total: Int? = null

    @SerializedName("Name")
    @Expose
    private var name: String? = null

    @SerializedName("Code")
    @Expose
    private var code: String? = null

    @SerializedName("ContentType")
    @Expose
    private var contentType: String? = null

    @SerializedName("Design")
    @Expose
    private var design: String? = null

    @SerializedName("Data")
    @Expose
    private var homePatchDetails: MutableList<HomePatchDetail?>? = null

    constructor(
        sort: Int?,
        total: Int?,
        name: String?,
        code: String?,
        contentType: String?,
        design: String?,
        homePatchDetails: MutableList<HomePatchDetail?>?
    ) {
        this.sort = sort
        this.total = total
        this.name = name
        this.code = code
        this.contentType = contentType
        this.design = design
        this.homePatchDetails = homePatchDetails
    }


    fun getSort(): Int? {
        return sort
    }

    fun setSort(sort: Int) {
        this.sort = sort
    }

    fun getTotal(): Int? {
        return total
    }

    fun setTotal(total: Int) {
        this.total = total
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String) {
        this.code = code
    }

    fun getContentType(): String? {
        return contentType
    }

    fun setContentType(contentType: String) {
        this.contentType = contentType
    }

    fun getDesign(): String? {
        return design
    }

    fun setDesign(design: String) {
        this.design = design
    }

    fun getData(): MutableList<HomePatchDetail?>? {
        return homePatchDetails
    }

    fun setData(homePatchDetail: MutableList<HomePatchDetail?>?) {
        this.homePatchDetails = homePatchDetail
    }
}