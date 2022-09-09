package com.example.feedapp.feed.holders

import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.R
import com.example.feedapp.feed.FeedAdapter
import com.example.feedapp.model.AllNewsFeed

class AllNewsViewHolder(itemView: View) : ItemViewHolder<AllNewsFeed>(itemView) {
    private val adapter = FeedAdapter()

    init {
        initView()
    }

    private fun initView() {
        val newsFeedList: RecyclerView = itemView.findViewById(R.id.topNewsFeedListView)
        newsFeedList.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        newsFeedList.addItemDecoration(DividerItemDecoration(itemView.context, LinearLayoutManager.VERTICAL).apply {
            setDrawable(ResourcesCompat.getDrawable(itemView.resources, R.drawable.bg_divider, itemView.context.theme)!!)
        })
        newsFeedList.adapter = adapter
    }

    override fun bindView(data: AllNewsFeed) {
        adapter.updateFeed(data.articles)
        itemView.findViewById<TextView>(R.id.topNewsFeedTitleView).text = "All News"
    }
}