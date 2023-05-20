package com.somename.thenewsapp

data class NewsData(
    val articles: ArrayList<Article?>?,
    val status: String?,
    val totalResults: Int?
)
