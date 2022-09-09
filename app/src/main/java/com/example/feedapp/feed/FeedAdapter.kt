package com.example.feedapp.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.R
import com.example.feedapp.feed.holders.*
import com.example.feedapp.model.*

class FeedAdapter : RecyclerView.Adapter<ItemViewHolder<out FeedItem>>() {
    private val data = mutableListOf<FeedItem>()
    override fun onCreateViewHolder(parent: ViewGroup, @FeedTypes viewType: Int): ItemViewHolder<out FeedItem> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FeedTypes.NEWS_ITEM -> {
                ArticleViewHolder(layoutInflater.inflate(R.layout.layout_new_article, parent, false))
            }
            FeedTypes.TICKER_FEED -> {
                TickersViewHolder(layoutInflater.inflate(R.layout.layout_ticker_feed, parent, false))
            }
            FeedTypes.TICKER_ITEM -> {
                TickerItemViewHolder(layoutInflater.inflate(R.layout.layout_ticker_item, parent, false))
            }
            FeedTypes.ALL_NEWS -> {
                AllNewsViewHolder(layoutInflater.inflate(R.layout.layout_top_news_feed, parent, false))
            }
            FeedTypes.TOP_NEWS -> {
                TopNewsViewHolder(layoutInflater.inflate(R.layout.layout_top_news_feed, parent, false))
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    fun updateFeed(newFeedData: List<FeedItem>) {
        data.clear()
        data.addAll(newFeedData)
        notifyItemRangeChanged(0, newFeedData.size)
    }

    fun updateFeed(position: Int, newFeedData: FeedItem) {
        data[position] = newFeedData
        notifyItemChanged(position)
    }


    override fun onBindViewHolder(holder: ItemViewHolder<out FeedItem>, position: Int) {
        when (holder) {
            is ArticleViewHolder -> holder.bindView(data[position] as Article)
            is TickersViewHolder -> holder.bindView(data[position] as TickerFeed)
            is TickerItemViewHolder -> holder.bindView(data[position] as Ticker)
            is TopNewsViewHolder -> holder.bindView(data[position] as TopNewsFeed)
            is AllNewsViewHolder -> holder.bindView(data[position] as AllNewsFeed)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is TickerFeed -> FeedTypes.TICKER_FEED
            is TopNewsFeed -> FeedTypes.TOP_NEWS
            is AllNewsFeed -> FeedTypes.ALL_NEWS
            is Ticker -> FeedTypes.TICKER_ITEM
            is Article -> FeedTypes.NEWS_ITEM
            else -> super.getItemViewType(position)
        }
    }
}