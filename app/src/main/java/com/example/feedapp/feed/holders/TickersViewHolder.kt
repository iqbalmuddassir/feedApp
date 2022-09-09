package com.example.feedapp.feed.holders

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.R
import com.example.feedapp.feed.FeedAdapter
import com.example.feedapp.model.TickerFeed

class TickersViewHolder(itemView: View) : ItemViewHolder<TickerFeed>(itemView) {
    private lateinit var tickerList: RecyclerView
    private val adapter = FeedAdapter()

    init {
        initView()
    }

    private fun initView() {
        tickerList = itemView.findViewById(R.id.tickerFeedListView)
        tickerList.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        if (tickerList.itemDecorationCount == 0) {
            tickerList.addItemDecoration(DividerItemDecoration(itemView.context, LinearLayoutManager.HORIZONTAL).apply {
                setDrawable(ResourcesCompat.getDrawable(itemView.resources, R.drawable.bg_divider, itemView.context.theme)!!)
            })
        }
        tickerList.hasFixedSize()
        tickerList.adapter = adapter
    }

    override fun bindView(data: TickerFeed) {
        val onSaveInstanceState = tickerList.layoutManager?.onSaveInstanceState()
        adapter.updateFeed(data.tickers)
        tickerList.layoutManager?.onRestoreInstanceState(onSaveInstanceState)
    }
}