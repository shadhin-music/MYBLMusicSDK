package com.shadhinmusiclibrary.fragments.amar_tunes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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
       val mWebview:WebView = requireView().findViewById(R.id.webview)
           mWebview.settings.javaScriptEnabled =true
        mWebview.getSettings().setLoadWithOverviewMode(true)
        mWebview.getSettings().setUseWideViewPort(true)
        mWebview.loadUrl("http://www.google.com")
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