package com.devjamesp.quoteoftheday

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuotesPagerAdapter(
    private val quotes: List<Quote>,
    private val isNameRevealed: Boolean
) : RecyclerView.Adapter<QuotesPagerAdapter.QuoteViewHolder>() {
    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvQuote: TextView by lazy {
            itemView.findViewById(R.id.tvQuote)
        }

        private val tvName: TextView by lazy {
            itemView.findViewById(R.id.tvName)
        }

        @SuppressLint("SetTextI18n")
        fun bind(quote: Quote, isNameRevealed: Boolean) {
            tvQuote.text = "\"${quote.quote}\""

            if (isNameRevealed) {
                tvName.text = "-${quote.name}-"
                tvName.visibility = View.VISIBLE
            } else {
                tvName.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actualPosition = position % quotes.size
        holder.bind(quotes[actualPosition], isNameRevealed)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

}