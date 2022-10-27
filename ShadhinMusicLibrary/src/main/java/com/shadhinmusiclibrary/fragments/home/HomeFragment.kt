package com.shadhinmusiclibrary.fragments.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.adapter.ParentAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.SearchClickCallBack
import com.shadhinmusiclibrary.data.model.HomeDataModel
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.fragments.amar_tunes.AmarTunesViewModel
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable

internal class HomeFragment : BaseFragment<HomeViewModel, HomeViewModelFactory>(),
    HomeCallBack, SearchClickCallBack {

    //mini music player
    private lateinit var llMiniMusicPlayer: CardView
    private lateinit var ivSongThumbMini: ImageView
    private lateinit var tvSongNameMini: TextView
    private lateinit var tvSingerNameMini: TextView
    private lateinit var tvTotalDurationMini: TextView
    private lateinit var ibtnSkipPreviousMini: ImageButton
    private lateinit var ibtnPlayPauseMini: ImageButton
    private lateinit var ibtnSkipNextMini: ImageButton

    private var dataAdapter: ParentAdapter? = null
    private var pageNum = 1
    private lateinit var viewModelAmaraTunes: AmarTunesViewModel

    //var page = -1
    var isLoading = false
    var isLastPage = false

    // var rbtData: RBTDATA? = null
    private lateinit var rvAllHome: RecyclerView
    private lateinit var footerAdapter: HomeFooterAdapter

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewModelFactory(): HomeViewModelFactory {
        return injector.factoryHomeVM
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.my_bl_sdk_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Homex", "onViewCreated Message: " + pageNum)
        uiInitMiniMusicPlayer(view)
        viewModel?.fetchHomeData(pageNum, false)
        viewModelAmaraTunes = ViewModelProvider(
            this,
            injector.factoryAmarTuneVM
        )[AmarTunesViewModel::class.java]
        // viewModelAmaraTunes.fetchRBTURL()

        observeData()
    }

    private fun observeData() {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)

        playerViewModel.startObservePlayerProgress(viewLifecycleOwner)

        viewModel?.homeContent?.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE

                viewDataInRecyclerView(res.data)
            } else {
                progressBar.visibility = GONE
            }
            isLoading = false
        }
        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMus ->
            if (itMus != null) {
                setupMiniMusicPlayerAndFunctionality(UtilHelper.getSongDetailToMusic(itMus))
            }
        }
        playerViewModel.playerProgress.observe(viewLifecycleOwner) {
            tvTotalDurationMini.text = it.currentPositionTimeLabel()
        }

        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) {
            miniPlayerPlayPauseState(it.isPlaying)
        }
        if (playerViewModel.isMediaDataAvailable()) {
            llMiniMusicPlayer.visibility = View.VISIBLE
        } else {
            llMiniMusicPlayer.visibility = View.GONE
        }
    }


    private fun viewDataInRecyclerView(homeData: HomeDataModel?) {
        if (dataAdapter == null) {
            footerAdapter = HomeFooterAdapter()

            dataAdapter = ParentAdapter(this, this)

            val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            isLoading = true
                            viewModel!!.fetchHomeData(++pageNum, false)
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })

            recyclerView.adapter = dataAdapter
        }
        /* viewModelAmaraTunes.urlContent.observe(viewLifecycleOwner) { res ->
             Log.e("TAG", "URL: " + res)
             if (res.status == Status.SUCCESS) {
                 this.rbtData = res.data?.data
             }
         }*/

        homeData.let {
            it?.data?.let { it1 ->
                dataAdapter?.setData(it1)
                dataAdapter?.notifyDataSetChanged()
            }
        }
        if (homeData?.total == pageNum) {
            isLastPage = true
            val config = ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build()
            val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
            recyclerView.adapter = ConcatAdapter(config, dataAdapter, footerAdapter)
        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val data = Bundle()
        data.putSerializable(
            AppConstantUtils.PatchItem,
            selectedHomePatchItem as Serializable
        )
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
            .apply {
                putExtra(AppConstantUtils.UI_Request_Type, AppConstantUtils.Requester_Name_Home)
                putExtra(AppConstantUtils.PatchItem, data)
                putExtra(AppConstantUtils.SelectedPatchIndex, itemPosition)
            })
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val data = Bundle()
        data.putSerializable(
            AppConstantUtils.PatchItem,
            selectedHomePatchItem as Serializable
        )
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
            .apply {
                putExtra(AppConstantUtils.UI_Request_Type, AppConstantUtils.Requester_Name_Home)
                putExtra(AppConstantUtils.PatchItem, data)
            })
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {

    }

    override fun clickOnSearchBar(selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val data = Bundle()
        data.putSerializable(
            AppConstantUtils.PatchItem,
            selectedHomePatchItem as Serializable
        )
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
            .apply {
                putExtra(
                    AppConstantUtils.UI_Request_Type,
                    AppConstantUtils.Requester_Name_Search
                )
                putExtra(AppConstantUtils.PatchItem, data)
            })
    }

    //Copy paste from SDKMainActivity
    private fun uiInitMiniMusicPlayer(view: View) {
        llMiniMusicPlayer = view.findViewById(R.id.include_mini_music_player)
        ivSongThumbMini = view.findViewById(R.id.iv_song_thumb_mini)
        tvSongNameMini = view.findViewById(R.id.tv_song_name_mini)
        tvSingerNameMini = view.findViewById(R.id.tv_singer_name_mini)
        tvTotalDurationMini = view.findViewById(R.id.tv_total_duration_mini)
        ibtnSkipPreviousMini = view.findViewById(R.id.ibtn_skip_previous_mini)
        ibtnPlayPauseMini = view.findViewById(R.id.ibtn_play_pause_mini)
        ibtnSkipNextMini = view.findViewById(R.id.ibtn_skip_next_mini)
    }

    //Copy paste from SDKMainActivity
    private fun setupMiniMusicPlayerAndFunctionality(mSongDetails: SongDetailModel) {
        Glide.with(this)
            .load(mSongDetails.getImageUrl300Size())
            .transition(DrawableTransitionOptions().crossFade(500))
            .fitCenter()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
            .placeholder(R.drawable.my_bl_sdk_default_song)
            .error(R.drawable.my_bl_sdk_default_song)
            .into(ivSongThumbMini)


        tvSongNameMini.text = mSongDetails.titleName
        tvSingerNameMini.text = mSongDetails.artistName
        tvTotalDurationMini.text = TimeParser.secToMin(mSongDetails.total_duration)
        llMiniMusicPlayer.visibility = View.VISIBLE


        ibtnSkipPreviousMini.setOnClickListener {
            playerViewModel.skipToPrevious()
        }

        ibtnPlayPauseMini.setOnClickListener {
            playerViewModel.togglePlayPause()
        }

        ibtnSkipNextMini.setOnClickListener {
            playerViewModel.skipToNext()
        }
    }

    //Copy paste from SDKMainActivity
    private fun miniPlayerPlayPauseState(playing: Boolean) {
        if (playing) {
            ibtnPlayPauseMini.setImageResource(R.drawable.my_bl_sdk_ic_baseline_pause_24)
        } else {
            ibtnPlayPauseMini.setImageResource(R.drawable.my_bl_sdk_ic_baseline_play_arrow_black_24)
        }
    }
}
