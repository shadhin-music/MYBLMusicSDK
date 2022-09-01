package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomeData


class AllGenreAdapter() : RecyclerView.Adapter<AllGenreAdapter.DataAdapterViewHolder>() {
   private val adapterData = mutableListOf<HomeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {

            VIEW_BROWSE_ALL ->  R.layout.item_genres

             else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position].data) {

           // is Artist -> VIEW_BROWSE_ALL


            else -> {
                throw IllegalArgumentException("Invalid view type")

            }
        }
    }

    fun setData(data: List<HomeData>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun bindArtist() {
            Log.d("Hello", "Loading")
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                GridLayoutManager(itemView.context,2)
            recyclerView.adapter = GenresAdapter()

        }

        private fun bindArtist2() {
            Log.d("Hello", "Loading")
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                GridLayoutManager(itemView.context,2)
            recyclerView.adapter = GenresAdapter()

        }
        private fun bindArtist3() {
            Log.d("Hello", "Loading")
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                GridLayoutManager(itemView.context,2)
            recyclerView.adapter = GenresAdapter()

        }


        fun bind(dataModel: HomeData) {
            when (dataModel) {
//                is Artist -> bindArtist()
//                is Artist2 -> bindArtist2()
//                is Artist3-> bindArtist2()
//                is Artist4 -> bindArtist2()
//                is DataModel.BlOffers -> bindBlOffers(dataModel)


            }
        }
    }


    companion object {

        val VIEW_BROWSE_ALL = 0
        val VIEW_BROWSE_ALL2 = 2
        val VIEW_BROWSE_ALL3 = 3

    }
}