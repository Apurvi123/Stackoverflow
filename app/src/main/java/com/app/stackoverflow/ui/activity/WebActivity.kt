package com.app.stackoverflow.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.app.stackoverflow.R
import com.app.stackoverflow.ui.util.Constants
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val intent = intent

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        webView.loadUrl(intent.getStringExtra(Constants.INTENT_KEY_URL))
    }
}
