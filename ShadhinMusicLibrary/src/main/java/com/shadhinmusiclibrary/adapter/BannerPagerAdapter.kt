package com.shadhinmusiclibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import java.util.*


class BannerPagerAdapter(
// Context object
    var context: Context,
    var homeListData: List<HomePatchDetailModel>, // Array of images
) :
    PagerAdapter() {
    // Layout Inflater
    var mLayoutInflater: LayoutInflater
    override fun getCount(): Int {
        // return the number of images
        return homeListData.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater.inflate(R.layout.my_bl_sdk_banner_image_layout, container, false)

        // referencing the image view from the item.xml file
        val imageView: ImageView = itemView.findViewById<View>(R.id.imageViewMain) as ImageView
        val imageurl = homeListData.get(position).bannerImage
//        // setting the image in the imageView
//        imageView.setImageResource(homeListData.get(position).bannerImage)
        Glide.with(context).load(imageurl?.replace("<\$size\$>", "984")).into(imageView)

        // Adding the View
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    // Viewpager Constructor
    init {
        homeListData = homeListData
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}