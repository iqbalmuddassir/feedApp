package com.example.feedapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.feedapp.R
import com.example.feedapp.feed.FeedAdapter
import com.example.feedapp.model.AllNewsFeed
import com.example.feedapp.model.Article
import com.example.feedapp.model.TickerFeed
import com.example.feedapp.model.TopNewsFeed
import com.example.feedapp.network.NewsRepository
import com.example.feedapp.network.TickerRepository
import com.example.feedapp.viewmodel.FeedViewModelFactory
import com.example.feedapp.viewmodel.NewsViewModel
import com.example.feedapp.viewmodel.TickerViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var tickerViewModel: TickerViewModel
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var feedListView: RecyclerView

    private val feedAdapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialiseViewModels()
        initialiseViews()
    }

    private fun initialiseViews() {
        feedListView = findViewById(R.id.feedListView)
        feedListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        (feedListView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        feedListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL).apply {
            setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.bg_divider, theme)!!)
        })

        feedAdapter.updateFeed(mutableListOf(TickerFeed(emptyList()), TopNewsFeed(emptyList()), AllNewsFeed(emptyList())))
        feedListView.adapter = feedAdapter
    }

    private fun initialiseViewModels() {
        val viewModelFactory = FeedViewModelFactory(TickerRepository(this), NewsRepository())

        tickerViewModel = ViewModelProvider(this, viewModelFactory)[TickerViewModel::class.java]
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        tickerViewModel.observeTickers().observe(this) {
            feedAdapter.updateFeed(0, TickerFeed(it))
        }

        newsViewModel.observeNewsFeed().observe(this) {
            addNewsFeeds(it)
        }
    }

    private fun addNewsFeeds(articles: List<Article>) {
        if (articles.isEmpty()) return
        feedAdapter.updateFeed(1, TopNewsFeed(articles.subList(0, 6)))
        feedAdapter.updateFeed(2, AllNewsFeed(articles))
    }
}