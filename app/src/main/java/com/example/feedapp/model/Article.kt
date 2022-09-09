package com.example.feedapp.model

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val urlToImage: String,
    val url: String,
    val publishedAt: String
): FeedItem
