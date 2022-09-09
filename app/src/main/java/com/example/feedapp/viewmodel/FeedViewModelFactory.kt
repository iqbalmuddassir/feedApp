package com.example.feedapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feedapp.network.NewsRepository
import com.example.feedapp.network.TickerRepository

class FeedViewModelFactory(private val tickerRepository: TickerRepository, private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TickerViewModel::class.java -> TickerViewModel(tickerRepository)
            NewsViewModel::class.java -> NewsViewModel(newsRepository)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}