package com.somename.thenewsapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BaseFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private val TAG = "BaseFragment"


    private lateinit var fragRecyclerView: RecyclerView
    private lateinit var fragAdapter: FragmentAdapter
    private lateinit var fragSomeEmptyList: ArrayList<Article>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            BaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        fragSomeEmptyList = arrayListOf<Article>()
        fragRecyclerView = view.findViewById(R.id.fragRecyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        fragRecyclerView.layoutManager = layoutManager
        fragAdapter = FragmentAdapter(requireContext(), fragSomeEmptyList)
        fragRecyclerView.adapter = fragAdapter


        fetchBusinessNews()


    }

    private fun fetchBusinessNews(){
        RetrofitInstance.apiInterface.getBusinessNews("in","business", "2f41782089b9486491796eccf5952348").enqueue(object:Callback<NewsData>{
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if(response.isSuccessful){
                    Log.e(TAG, "Yep Business News Success")
                    val articles = response.body()?.articles
                    fragAdapter.setArticles(articles)
                }
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.e(TAG, "Nope it's Failed")
                Toast.makeText(requireContext(), "News is refreshing, just a minute", Toast.LENGTH_LONG).show()
            }
        })
    }

}