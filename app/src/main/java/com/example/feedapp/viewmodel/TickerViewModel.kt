package com.example.feedapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedapp.model.Ticker
import com.example.feedapp.network.TickerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class TickerViewModel(private val tickerRepository: TickerRepository) : ViewModel() {

    init {
        startTickerListener()
    }

    private val tickers: MutableLiveData<List<Ticker>> = MutableLiveData(emptyList())

    private fun startTickerListener() {
        viewModelScope.launch {
            startTickerFlow()
                .flowOn(Dispatchers.Main)
                .collect { tickers.postValue(it) }
        }
    }

    fun observeTickers(): LiveData<List<Ticker>> = tickers

    private suspend fun startTickerFlow(): Flow<List<Ticker>> {
        return flow {
            do {
                val tickers = tickerRepository.refreshTicker()
                emit(tickers)
                delay(DELAY_MS)
            } while (viewModelScope.isActive)
        }.flowOn(Dispatchers.IO)
            .catch { println("#mi:: TickerViewModel -> catch: error occurred!!, $it") }
    }

    companion object {
        @OptIn(ExperimentalTime::class)
        val DELAY_MS = 1.seconds.inWholeMilliseconds
    }
}