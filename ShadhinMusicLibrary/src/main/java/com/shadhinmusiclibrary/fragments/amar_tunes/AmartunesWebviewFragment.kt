package com.shadhinmusiclibrary.fragments.amar_tunes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.RBTDATA
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.utils.DataContentType.AMR_TUNE
import com.shadhinmusiclibrary.utils.DataContentType.AMR_TUNE_ALL

import com.shadhinmusiclibrary.utils.DataContentType.CONTENT_TYPE
import com.shadhinmusiclibrary.utils.Status


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


internal class AmartunesWebviewFragment : Fragment(), FragmentEntryPoint {
    var data: RBTDATA? = null
    private lateinit var viewModelAmaraTunes: AmarTunesViewModel
    private var contentType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable("data") as RBTDATA?
            contentType = it.getString(CONTENT_TYPE)
            Log.d("TAG", "DATA: " + data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.my_bl_sdk_fragment_amar_tunes_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelAmaraTunes = ViewModelProvider(
            this,
            injector.factoryAmarTuneVM
        )[AmarTunesViewModel::class.java]
        viewModelAmaraTunes.fetchRBTURL()

        observeData()


    }

    private fun observeData() {

        viewModelAmaraTunes.urlContent.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {

                val redirectUrl: String = res?.data?.data?.redirectUrl.toString()

                val url = when (contentType) {
                    AMR_TUNE_ALL -> res?.data?.data?.pwaUrl
                    AMR_TUNE -> res?.data?.data?.pwatopchartURL
                    else -> null
                }
                if (url != null) {
                    openWebView(url, redirectUrl)
                } else {
                    Toast.makeText(requireActivity(), "URL NULL", Toast.LENGTH_SHORT).show();
                }
            } else {
               if(res.errorCode == 401){
                   Toast.makeText(requireActivity(), "Unauthorized", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(requireActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
               }

            }
        }
    }

    private fun openWebView(url: String, redirectUrl: String) {
        val mWebview: WebView = requireView().findViewById(R.id.webview)
        //  mWebview.settings.javaScriptEnabled =true
        mWebview.getSettings().setLoadsImagesAutomatically(true)
        mWebview.getSettings().setJavaScriptEnabled(true)
        mWebview.getSettings().setAllowContentAccess(true)

//        mWebview.loadUrl("http://amartune.banglalink.net/pwa/home")

        mWebview.getSettings().setUseWideViewPort(true)
        mWebview.getSettings().setLoadWithOverviewMode(true)
        mWebview.getSettings().setDomStorageEnabled(true)
        mWebview.clearView()
        mWebview.setHorizontalScrollBarEnabled(false)
        mWebview.getSettings().setAppCacheEnabled(true)
        mWebview.getSettings().setDatabaseEnabled(true)
        mWebview.setVerticalScrollBarEnabled(false)
        mWebview.getSettings().setBuiltInZoomControls(true)
        mWebview.getSettings().setDisplayZoomControls(false)
        mWebview.getSettings().setAllowFileAccess(true)
        mWebview.getSettings().setPluginState(WebSettings.PluginState.OFF)
        mWebview.setScrollbarFadingEnabled(false)
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE)
        mWebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR)
        mWebview.setWebViewClient(WebViewClient())
        mWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH)
        mWebview.setInitialScale(1)
        mWebview.loadUrl(url)
        //Log.e("TAG", "URL: " + pwatopchartURL)
        mWebview.setWebViewClient(MyWebViewClient(redirectUrl))
    }

    inner class MyWebViewClient(val redirectUrl: String) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val mWebview: WebView? = view?.findViewById(R.id.webview)
            //  view.loadUrl(url)

            if (url.equals(redirectUrl)) {
                /*  // Toast.makeText(mWebview?.context,"CLOSE",Toast.LENGTH_SHORT).show()
                      (view.context as AppCompatActivity).supportFragmentManager.popBackStack()*/

                requireActivity().onBackPressed()
//
            }

            // view.canGoBack()

            return true
        }

//        override fun onPageFinished(view: WebView?, url: String?) {
//            val mWebview:WebView ?= view?.findViewById(R.id.webview)
//            if(url.equals("http://amartune.banglalink.net/pwa/home")){
//                mWebview?.goBack()
////                getActivity().getFragmentManager().beginTransaction().remove(con).commit();
////                val manager: FragmentManager =
////                    (con as AppCompatActivity).supportFragmentManager
////                manager?.popBackStack("Fragment", 0)
//              // view?.destroy()
//                Log.e("TAG","URL123: "+ url)
//            }

        // Log.e("TAG","URL43: "+ url)
//            view?.loadUrl(url.equals("https://api.shadhinmusic.com/api/v5/mybl/amartunecb").toString())
//            view?.canGoBack()
        //   }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AmartunesWebviewFragment().apply {
                arguments = Bundle().apply {
                    //putSerializable("data", data)
//                    putString(ARG_PARAM2, param2)
                }
            }
//        @JvmStatic
//        fun newInstance(data: RBTDATA) =
//            AmartunesWebviewFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable("data", data)
////                    putString(ARG_PARAM2, param2)
//                }
//            }
    }


    override fun onResume() {
        super.onResume()
       kotlin.runCatching { (activity as AppCompatActivity?)?.supportActionBar?.hide() }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
//    override fun getViewModel(): Class<HomeViewModel> {
//        return HomeViewModel::class.java
//    }

//    override fun getViewModelFactory(): HomeViewModelFactory {
//        return injector.factoryAmarTuneVM
//    }
}