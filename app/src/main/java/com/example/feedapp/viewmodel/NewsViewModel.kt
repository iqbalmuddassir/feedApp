package com.example.feedapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedapp.model.Article
import com.example.feedapp.network.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    init {
        pullNewsFromServer()
    }

    private val newsFeed: MutableLiveData<List<Article>> = MutableLiveData(emptyList())

    private fun pullNewsFromServer() {
        viewModelScope.launch {
            pullNewsFromServerInternal()
                .flowOn(Dispatchers.Main)
                .collect { newsFeed.postValue(it) }
        }
    }

    fun observeNewsFeed(): LiveData<List<Article>> = newsFeed

    private suspend fun pullNewsFromServerInternal(): Flow<List<Article>> {
        return flow {
            if (viewModelScope.isActive) {
                val tickers = newsRepository.fetchNews()
                emit(tickers)
            }
        }.flowOn(Dispatchers.IO)
            .catch { println("#mi:: NewsViewModel -> catch: error occurred!!, $it") }
    }
}