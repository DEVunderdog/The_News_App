package com.somename.thenewsapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.somename.thenewsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY != 0){
            val intent = Intent(this, NewsFeed::class.java)
            startActivity(intent)
        }

        if(supportActionBar != null){
            supportActionBar!!.hide()
        }


        if(Build.VERSION.SDK_INT >= 21){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.grey_200)
        }

        val btnAhead = binding.moveAhead

        btnAhead.setOnClickListener {
            val intent = Intent(this, NewsFeed::class.java)
            startActivity(intent)
            finish()
        }
    }
}