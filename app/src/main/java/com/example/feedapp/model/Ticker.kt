package com.example.feedapp.model

data class Ticker(val symbol: String, val value: Double): FeedItem {
    companion object {
        val EMPTY = Ticker("", 0.0)
    }
}
