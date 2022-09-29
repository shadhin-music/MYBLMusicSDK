package com.shadhinmusiclibrary.fragments.amar_tunes

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.R

class AmartunesWebviewFragment : Fragment() {

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
        mWebview.loadUrl("http://amartune.banglalink.net/pwa/home")
        //  Log.e("TAG", "URL: "+ "http://amartune.banglalink.net/pwa/home")
        mWebview.setWebViewClient(MyWebViewClient())
    }

    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
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
}