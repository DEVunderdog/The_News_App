package com.somename.thenewsapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.somename.thenewsapp.databinding.ActivityNewsFeedBinding
import com.somename.thenewsapp.databinding.ActivityReadNewsBinding

@Suppress("DEPRECATION")
class ReadNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null){
            supportActionBar!!.hide()
        }

        if(Build.VERSION.SDK_INT >= 21){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.grey_200)
        }

        val url = intent.getStringExtra("URL")
        if(url != null){
            binding.newsWebView.settings.javaScriptEnabled = true
            binding.newsWebView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36`"
            binding.newsWebView.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                    binding.newsWebView.visibility = View.VISIBLE
                }
            }
            binding.newsWebView.loadUrl(url)


        }
    }
}