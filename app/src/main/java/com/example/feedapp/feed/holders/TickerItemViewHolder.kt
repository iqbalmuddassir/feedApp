package com.example.feedapp.feed.holders

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.feedapp.R
import com.example.feedapp.model.Ticker
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs

class TickerItemViewHolder(itemView: View) : ItemViewHolder<Ticker>(itemView) {

    override fun bindView(data: Ticker) {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        itemView.findViewById<TextView>(R.id.tickerNameView).text = data.symbol
        val ticketValueView = itemView.findViewById<TextView>(R.id.tickerValueView)
        if (data.value < 0) {
            ticketValueView.setTextColor(Color.parseColor("#FF0000"))
        } else {
            ticketValueView.setTextColor(Color.parseColor("#50C878"))
        }
        ticketValueView.text = df.format(abs(data.value))

    }
}