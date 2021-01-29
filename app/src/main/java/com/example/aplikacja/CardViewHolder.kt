package com.example.aplikacja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindModelToView(model: CardViewItem): Unit = with(itemView) {
        val textView = this.findViewById<TextView>(R.id.testView)

        textView.text = model.text
    }

    companion object {
        fun create(parent: ViewGroup): CardViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.card_view_item,
                parent,
                false
            )
            return CardViewHolder(view)
        }
    }
}