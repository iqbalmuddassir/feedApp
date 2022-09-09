package com.example.feedapp.feed.holders

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.R
import com.example.feedapp.feed.FeedAdapter
import com.example.feedapp.model.TopNewsFeed

class TopNewsViewHolder(itemView: View) : ItemViewHolder<TopNewsFeed>(itemView) {
    private val adapter = FeedAdapter()
    private fun initView() {
        val newsFeedListView: RecyclerView = itemView.findViewById(R.id.topNewsFeedListView)
        newsFeedListView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        newsFeedListView.addItemDecoration(DividerItemDecoration(itemView.context, LinearLayoutManager.HORIZONTAL).apply {
            setDrawable(ResourcesCompat.getDrawable(itemView.resources, R.drawable.bg_divider, itemView.context.theme)!!)
        })
        newsFeedListView.adapter = adapter
    }

    init {
        initView()
    }

    override fun bindView(data: TopNewsFeed) {
        adapter.updateFeed(data.articles)
    }
}