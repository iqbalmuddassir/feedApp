package com.example.feedapp.network

import com.example.feedapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {

    @GET("NewsAPI/everything/cnn.json")
    fun refreshNews(): Call<NewsResponse>
}