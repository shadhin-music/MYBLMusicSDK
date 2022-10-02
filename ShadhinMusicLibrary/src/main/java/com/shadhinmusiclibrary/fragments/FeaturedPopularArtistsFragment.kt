import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.FeaturedPopularArtistAdapter
import com.shadhinmusiclibrary.callBackService.PatchCallBack
import com.shadhinmusiclibrary.data.model.Data
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.artist.PopularArtistViewModel
import com.shadhinmusiclibrary.utils.Status

class FeaturedPopularArtistFragment : Fragment(), PatchCallBack, FragmentEntryPoint {
    private var homePatchitem: HomePatchItem? = null

    lateinit var viewModel: PopularArtistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            homePatchitem = it.getSerializable("homePatchitem") as HomePatchItem?
//           // Log.d("TaG","Message123: "+ homePatchitem)
//        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                injector.popularArtistViewModelFactory
            )[PopularArtistViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container1: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_featured_popular_artist, container1, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaG", "Message: " + homePatchitem)
        val tvTitle: TextView = requireView().findViewById(R.id.tvTitle)
        //tvTitle.text =  homePatchitem?.Name
        setupViewModel()
        observeData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
//            Log.d("TAGGGGGGGY", "MESSAGE: ")
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager?.popBackStack("Fragment", 0);
            // ShadhinMusicSdkCore.getHomeFragment()
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container1, HomeFragment())
//                .addToBackStack(null)
//                .commit()
//            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
//                requireActivity().finish()
//            }
        }
    }

    fun observeData() {
        viewModel.fetchPouplarArtist()
        viewModel.popularArtistContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 4)
//          Log.e("TAG","ID: "+ argHomePatchItem)
                recyclerView.adapter =
                    response.data?.let {
                        it.data.let { it1 ->
                            FeaturedPopularArtistAdapter(
                                it1,
                                this
                            )
                        }
                    }
            } else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
            }
        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedData: List<Data>) {

    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance()=
////        fun newInstance(homePatchitem: HomePatchItem) =
//            FeaturedPopularArtistFragment().apply {
//                arguments = Bundle().apply {
//                    //   putSerializable("homePatchitem", homePatchitem)
//
//                }
//            }
//    }

//    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
//
//    }
//
//    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
//
//    }
//
//    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
//
//    }
}
