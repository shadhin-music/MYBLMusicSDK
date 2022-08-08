package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.view_holder.BaseViewHolder

internal class AllAdapter() :
    RecyclerView.Adapter<AllAdapter.AllViewHolder>() {

    val VIEW_AMER_TUNES = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
        return AllViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.parent_music_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        holder.onBind(position);
        holder.rvMusic.apply {
            layoutManager =
                LinearLayoutManager(holder.rvMusic.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MusicAdapter()
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    public fun setMusic() {

    }

    internal class AllViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val includeMusicLeftIconParent: LinearLayout =
            itemView.findViewById(R.id.include_music_left_icon_parent)
        val ivMusicParentIcon: ImageView =
            includeMusicLeftIconParent.findViewById(R.id.iv_music_parent_icon)
        val tvMusicParentTitle: TextView =
            includeMusicLeftIconParent.findViewById(R.id.tv_music_parent_title)

        private val includeMusicRightParentSeeAll: LinearLayout =
            itemView.findViewById(R.id.include_music_right_parent_see_all)
        val tvMusicRightParentSeeAllTitle: TextView =
            includeMusicRightParentSeeAll.findViewById(R.id.tv_see_all_title)
        val ivMusicRightParentSeeAllIcon: ImageView =
            includeMusicRightParentSeeAll.findViewById(R.id.iv_see_all_icon)

        val rvMusic: RecyclerView = itemView.findViewById(R.id.rv_music)

        private val includeMyTurnLeftTitle: LinearLayout =
            itemView.findViewById(R.id.include_my_turn_left_title)
        val tvMyTurnLeftChildTitle: TextView =
            includeMyTurnLeftTitle.findViewById(R.id.tv_music_child_title)

        private val includeMyTurnRightSeeAll: LinearLayout =
            itemView.findViewById(R.id.include_my_turn_right_see_all)
        val tvMyTurnRightSeeAllTitle: TextView =
            includeMyTurnRightSeeAll.findViewById(R.id.tv_see_all_title)
        val ivMyTurnRightSeeAllIcon: ImageView =
            includeMyTurnRightSeeAll.findViewById(R.id.iv_see_all_icon)

        private val includeThousandSongLayout: CardView =
            itemView.findViewById(R.id.include_thousand_song_layout)
        val tvThousandSongMessage1: TextView =
            includeThousandSongLayout.findViewById(R.id.tv_thousand_song_message1)
        val tvThousandSongMessage2: TextView =
            includeThousandSongLayout.findViewById(R.id.tv_thousand_song_message2)
        val tvThousandSongMessage3: TextView =
            includeThousandSongLayout.findViewById(R.id.tv_thousand_song_message3)
        val btnExplore: Button = includeThousandSongLayout.findViewById(R.id.btn_explore)

        private val includeRadioLeftTitle: LinearLayout =
            itemView.findViewById(R.id.include_radio_left_title)
        val tvRadioLeftChildTitle: TextView =
            includeRadioLeftTitle.findViewById(R.id.tv_music_child_title)

        val includeRadioRightSeeAll: LinearLayout =
            itemView.findViewById(R.id.include_radio_right_see_all)
        val tvRadioRightSeeAllTitle: TextView =
            includeMyTurnRightSeeAll.findViewById(R.id.tv_see_all_title)
        val ivRadioRightSeeAllIcon: ImageView =
            includeMyTurnRightSeeAll.findViewById(R.id.iv_see_all_icon)

        private val includeRadioLayout: CardView =
            itemView.findViewById(R.id.include_radio_layout)
        val sivRadioIcon: ShapeableImageView =
            includeRadioLayout.findViewById(R.id.siv_radio_icon)
        val tvRadioName: TextView =
            includeRadioLayout.findViewById(R.id.tv_radio_name)
        val ivRadioFavorite: ImageView =
            includeRadioLayout.findViewById(R.id.iv_radio_favorite)
        val ivRadioPlay: ImageView =
            includeRadioLayout.findViewById(R.id.iv_radio_play)

        private val includePromotionalAdsLayout: CardView =
            itemView.findViewById(R.id.include_promotional_ads_layout)
        val sivPromotionalAdsIcon: ShapeableImageView =
            includePromotionalAdsLayout.findViewById(R.id.siv_promotional_ads_icon)

        //private var item:
        override fun onBind(position: Int) {
            super.onBind(position)
        }
    }
}