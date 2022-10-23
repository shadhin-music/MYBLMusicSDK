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

internal class AmartunesWebviewFragment : Fragment(), FragmentEntryPoint {
    var data: RBTDATA? = null
    private lateinit var viewModelAmaraTunes: AmarTunesViewModel
    private var contentType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable("data") as RBTDATA?
            contentType = it.getString(CONTENT_TYPE)
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
//        val jsonObject = JSONObject()
//        jsonObject.put("first_name", "")
//        jsonObject.put("last_name", "")
//        jsonObject.put("gender", "")
//        jsonObject.put("msisdn","8801960685935")
//
//
//        val jsonObjectString = jsonObject.toString()

        //  val token = "eN3Zs2kWO0CEvRxj08ZVFRw8f9Xn5l27PDsHZeO+tEIY3rib1TxvsH4qefC43WqNxuMMXew3gFEkvUhvl+2RRt/1G2lM1+XJ60xp7nqx/IBoaPx6OKUpih1XbQGnmXanOUvkroC4ENHC57/OLszsG2R4LY93bS3e/5TL/GBx1iXoFfnQxVyssUdmnv2DmE//gBPA9FYOHKiHs3XBClOGd+qE5b2kxnR/c9r+IPd2sodhQoeuQVfx4RWiohZRYbr/RmAYTuN5ctuxq3D2PXx+rtD5n4fFlvGNG3w2zamyCRj2+lvbYmBOJ8psphoySQx8pF6A29qVyIq+m0DkNQI21RvRqx3+/+CzNHDYx5Nq4hIxM21UfAa/6a2oNtO3NpIxSO7UUbN5s2DnqOW2Ic7yWFU7UHkrM1NYDCTwpL3nB3Esz95Zzp1uumN1689R47L7wpJuTemOY4NWruLMsEzD/btbYt00vbsnn+YZsdlyxxtUj3PghEXZ0p0p0sjlcnJef/628UpwFPr1SF4AktARDGqxGBU6r2iD4eEI7RtNdUC00mSfHmyi7r22oxygxmnXLxNNNPFZYZYC4hXGTTA9tXxoLfW5Y9woaPtZtj18Y0pkBh+vTsiGr4Bb7QNRvw3xDarR5wUrPFCRKXb/crIz+veHagvqcffQpm9UxT3eVyDZaP3/HBDuI7ZokLGvOMy8UE9NOfozgBaE/cgSi4Hr/t0d/qS1y8h4KG2gTdiGcL7j4d3wg9LsHS5nKswu99+uheceeh0K3IKp22EDZDAwWBXaCjTTAFzcX2ReGJDUu2w82dtzgtGNTD6z4m7Z/tY+2jTM/YsjsMJN5W5lFv906bZ+3K7qd/SYmksbbRyWFTaAmRb4vdX4G4UR5s/3nwLXMfLnPhlmSOOqwdWlqOxqLTgcj0HzcWY40+4HXYYo2zs=:u3Qghadl765gySHMQ8W2yA=="
        // val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        //viewModel!!.fetchRBTURL()
        observeData()
    }

    private fun observeData() {
//        viewModelAmaraTunes.urlContent.observe(viewLifecycleOwner){res->
//
//            if (res.status == Status.SUCCESS) {
//
//                val redirectUrl: String = res?.data?.data?.redirectUrl.toString()
//
//
//
//                val url = when(contentType){
//                      AMR_TUNE_ALL -> res?.data?.data?.pwaUrl.toString()
//                      AMR_TUNE -> res?.data?.data?.pwatopchartURL.toString()
//                      else -> null
//                  }
//                if (url != null) {
//                    openWebView(url,redirectUrl)
//                }
//            }
//        }
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
                Toast.makeText(requireActivity(), "Server Error", Toast.LENGTH_SHORT).show();
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

    override fun onResume() {
        super.onResume()
        kotlin.runCatching { (activity as AppCompatActivity?)?.supportActionBar?.hide() }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}