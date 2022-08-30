package com.shadhinmusiclibrary.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.GenreDataModel


class PlaylistAdapter() : RecyclerView.Adapter<PlaylistAdapter.DataAdapterViewHolder>() {
    private val adapterData = mutableListOf<GenreDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
            VIEW_CURRENT_PLAY_ITEM -> R.layout.playlist_header
            VIEW_BROWSE_ALL -> R.layout.latest_music_view_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return DataAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(adapterData[position])

        holder.itemView.setOnClickListener {
            if (position > 0) {
                Toast.makeText(holder.itemView.context, "PLA test", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is GenreDataModel.Artist -> VIEW_CURRENT_PLAY_ITEM
            is GenreDataModel.Artist2 -> VIEW_BROWSE_ALL
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    fun setData(data: List<GenreDataModel>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun bindArtist() {
            Log.d("Hello", "Loading")
//            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
//            recyclerView.layoutManager =
//                GridLayoutManager(itemView.context,2)
//            recyclerView.adapter = GenresAdapter()
        }

        private fun bindMenuBottomSheet() {
            val ivMenu: ImageView = itemView.findViewById(R.id.iv_song_menu_icon)
            ivMenu.setOnClickListener {
                showBottomSheetDialog(itemView.context)
            }
            Log.d("Hello", "Loading")
//            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
//            recyclerView.layoutManager =
//                GridLayoutManager(itemView.context,2)
//            recyclerView.adapter = GenresAdapter()
        }

        private fun bindArtist3() {
            Log.d("Hello", "Loading")
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = GridLayoutManager(itemView.context, 2)
            recyclerView.adapter = GenresAdapter()
        }

        fun bind(dataModel: GenreDataModel) {
            when (dataModel) {
                is GenreDataModel.Artist -> bindArtist()
                is GenreDataModel.Artist2 -> bindMenuBottomSheet()
                is GenreDataModel.Artist3 -> bindArtist()
                is GenreDataModel.Artist4 -> bindMenuBottomSheet()
//                is DataModel.BlOffers -> bindBlOffers(dataModel)
            }
        }

        fun showBottomSheetDialog(context: Context) {
            val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            val contentView =
                View.inflate(context, R.layout.bottomsheet_three_dot_menu_layout, null)
            bottomSheetDialog.setContentView(contentView)
            //  bottomSheetDialog.setContentView(R.layout.bottomsheet_three_dot_menu_layout)
            //bottomSheetDialog.setBackgroundColor(getResources().getColor(android.R.color.transparent))
            //contentView.setBackgroundResource(R.drawable.dialog_bg)

            //(contentView.parent as View).setBackgroundColor(context.getResources().getColor(android.R.color.transparent))
            bottomSheetDialog.show()
        }
    }

    companion object {
        const val VIEW_CURRENT_PLAY_ITEM = 0
        const val VIEW_BROWSE_ALL = 2
        val VIEW_BROWSE_ALL3 = 3
    }
}