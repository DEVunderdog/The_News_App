package com.somename.thenewsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.somename.thenewsapp.databinding.ActivityNewsFeedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFeed : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private val TAG = "NewsFeed"
    private lateinit var binding: ActivityNewsFeedBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var someEmptyList:ArrayList<Article>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navView = binding.myNavView
        drawerLayout = binding.myDrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener(this)


        someEmptyList = arrayListOf<Article>()

        recyclerView = binding.MyRecyclerView
        adapter = NewsAdapter(this, someEmptyList)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        swipeRefreshLayout = binding.mySwipeRefreshLayout
        fetchOnStartupNews()
        swipeRefreshLayout.setOnRefreshListener {
            refreshAlertDialog()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Actually, You can't refresh here because API provide limited Top Headlines")
        builder.setTitle("Just Message")
        builder.setCancelable(false)
        builder.setPositiveButton("Got It !!!"){
                dialog, _ -> dialog.cancel()
            swipeRefreshLayout.isRefreshing = false
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun fetchOnStartupNews(){
        RetrofitInstance.apiInterface.getTopHeadlines("in", "2f41782089b9486491796eccf5952348").enqueue(object :
            Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if(response.isSuccessful){
                    Log.e(TAG, "Yep its success")
                    val articles = response.body()?.articles
                    adapter.setArticles(articles)
                }
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.e(TAG, "Nope it's failed")
                Toast.makeText(this@NewsFeed, "News is refreshing, wait a minute.", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun changeToFragment(frag: Fragment, title: String){
        Log.d(TAG, "change Fragment")
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "navigation got clicked")
        if(item.itemId == R.id.catBusiness){
            changeToFragment(BaseFragment(), "Business News")
        }
        if(item.itemId == R.id.catEntertainment){
            changeToFragment(EntertainmentFragment(), "Entertainment News")
        }
        if(item.itemId == R.id.catTech){
            changeToFragment(TechFragments(), "Technology News")
        }
        if(item.itemId == R.id.catSports){
            changeToFragment(SportsFragments(), "Sports News")
        }
        if(item.itemId == R.id.catHealth){
            changeToFragment(HealthFragment(), "Health News")
        }
        if(item.itemId == R.id.Home){
            val intent = Intent(this, NewsFeed::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }


}