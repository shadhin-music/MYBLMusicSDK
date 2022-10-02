package com.shadhinmusiclibrary.adapter.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.model.search.TopTrendingdata

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mCurrentPosition = 0
    open fun onBind(position: Int) {
        mCurrentPosition = position
    }
}