package com.shadhinmusiclibrary.adapter


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shadhinmusiclibrary.fragments.AllDownloadDetailsFragment
import com.shadhinmusiclibrary.fragments.PodcastDownloadFragment
import com.shadhinmusiclibrary.fragments.SongsDownloadFragment
import com.shadhinmusiclibrary.fragments.VideosDownloadFragment
import com.shadhinmusiclibrary.fragments.fav.*
import com.shadhinmusiclibrary.fragments.fav.ArtistFavFragment
import com.shadhinmusiclibrary.fragments.fav.PodcastFavFragment
import com.shadhinmusiclibrary.fragments.fav.SongsFavoriteFragment
import com.shadhinmusiclibrary.fragments.fav.VideosFavFragment


@Suppress("DEPRECATION")
internal class FavoriteViewPagerAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
//        return  SearchFragment()
        return when (position) {

            0 -> {
               SongsFavoriteFragment()

            }
           1-> {
              VideosFavFragment()

            }
            2-> {
               PodcastFavFragment()

            }

            3-> {
               ArtistFavFragment()

            }
            4-> {
               AlbumsFavFragment()

            }
            5-> {
               PlaylistFavFragment()

            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
