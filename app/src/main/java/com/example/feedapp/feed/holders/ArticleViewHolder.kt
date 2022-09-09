package com.example.feedapp.feed.holders

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.feedapp.R
import com.example.feedapp.model.Article


class ArticleViewHolder(itemView: View) : ItemViewHolder<Article>(itemView) {
    private lateinit var articleImageView: ImageView
    private lateinit var articleTitleView: TextView
    private lateinit var articleDescriptionView: TextView

    init {
        initViews()
    }

    private fun initViews() {
        articleImageView = itemView.findViewById(R.id.articleImageView)
        articleTitleView = itemView.findViewById(R.id.articleTitleView)
        articleDescriptionView = itemView.findViewById(R.id.articleDescriptionView)
    }

    override fun bindView(data: Article) {
        articleTitleView.text = data.title
        articleDescriptionView.text = data.description

        Glide.with(articleImageView).load(data.urlToImage).centerCrop().into(articleImageView)

        itemView.setOnClickListener {
            val url = data.url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(itemView.context, i, null)
        }
    }
}