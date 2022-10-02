package com.shadhinmusiclibrary.fragments.amar_tunes

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.internal.ContextUtils.getActivity
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.home.HomeViewModel
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.utils.Status
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AmartunesWebviewFragment : BaseFragment<HomeViewModel, HomeViewModelFactory>(),FragmentEntryPoint {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_amar_tunes_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonObject = JSONObject()
        jsonObject.put("first_name", "")
        jsonObject.put("last_name", "")
        jsonObject.put("gender", "")
        jsonObject.put("msisdn","8801960685935")


        val jsonObjectString = jsonObject.toString()


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        viewModel!!.fetchRBTURL(requestBody)
        observeData()


    }

    private fun observeData() {
        viewModel!!.urlContent.observe(viewLifecycleOwner){res->
            if (res.status == Status.SUCCESS) {
                //  viewDataInRecyclerView(argHomePatchItem, rbt!!.data)
                var url:String = res?.data?.data?.pwaUrl.toString()
                var redirectUrl: String = res?.data?.data?.redirectUrl.toString()
                OpenWebView(url,redirectUrl)
            }
        }
    }

    private fun OpenWebView(url: String, redirectUrl: String){
        val mWebview:WebView = requireView().findViewById(R.id.webview)
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mWebview.getSettings()
                .setDatabasePath("/data/data/" + this.toString() + "/databases/")
        }
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
        // mWebview.loadUrl("http://www.google.com")
        mWebview.loadUrl(url)
          Log.e("TAG", "URL: "+ url)
        mWebview.setWebViewClient(MyWebViewClient(redirectUrl, context))
    }
    private class MyWebViewClient(val redirectUrl: String, context: Context?) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val mWebview:WebView ?= view?.findViewById(R.id.webview)
            view.loadUrl(url)
             if (url.equals("http://amartune.banglalink.net/pwa/home")){


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
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewModelFactory(): HomeViewModelFactory {
        return injector.factoryHomeVM
    }
}