package com.somename.thenewsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("country")country:String, @Query("apiKey") apiKey: String): Call<NewsData>

    @GET("top-headlines")
    fun getBusinessNews(@Query("country")country: String,@Query("category")category:String, @Query("apiKey")apiKey: String): Call<NewsData>

    @GET("top-headlines")
    fun getEntertainmentNews(@Query("country")country: String, @Query("category")category: String, @Query("apiKey")apiKey: String):Call<NewsData>

    @GET("top-headlines")
    fun getHealthNews(@Query("country")country: String, @Query("category")category: String, @Query("apiKey")apiKey: String):Call<NewsData>

    @GET("top-headlines")
    fun getSportsNews(@Query("country")country: String, @Query("category")category: String, @Query("apiKey")apiKey: String):Call<NewsData>

    @GET("top-headlines")
    fun getTechNews(@Query("country")country: String, @Query("category")category: String, @Query("apiKey")apiKey: String):Call<NewsData>
}