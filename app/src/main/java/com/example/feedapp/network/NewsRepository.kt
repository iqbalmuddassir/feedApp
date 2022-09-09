package com.example.feedapp.network

import com.example.feedapp.model.Article
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository() {

    fun fetchNews(): List<Article> {
        val service = tickerApiService()
        val response = service.refreshNews().execute()
        return response.body()?.articles.toSafe()
    }

    private fun tickerApiService(): NewsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsApiService::class.java)
    }

    private fun <E> List<E>?.toSafe() = this ?: emptyList()

    companion object {
        const val BASE_URL = "https://saurav.tech/"
    }
}