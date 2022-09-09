package com.example.feedapp.feed

import androidx.annotation.IntDef

@IntDef(
    value = [
        FeedTypes.TICKER_FEED,
        FeedTypes.TICKER_ITEM,
        FeedTypes.TOP_NEWS,
        FeedTypes.ALL_NEWS,
        FeedTypes.NEWS_ITEM,
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class FeedTypes {
    companion object {
        const val TICKER_FEED = 1
        const val TICKER_ITEM = 2
        const val TOP_NEWS = 3
        const val ALL_NEWS = 4
        const val NEWS_ITEM = 5
    }
}
