package com.example.feedapp.feed.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.model.FeedItem

abstract class ItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) where T : FeedItem {
    abstract fun bindView(data: T)
}