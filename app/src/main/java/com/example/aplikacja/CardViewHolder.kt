package com.example.aplikacja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.round

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindModelToView(model: CardViewItem, position: Int): Unit = with(itemView) {
        val leftText = this.findViewById<TextView>(R.id.leftText)
        val rightText = this.findViewById<TextView>(R.id.rightText)
        val wydatek: MutableList<String> = model.wydatki.subList(position * 4, position * 4 + 4).asReversed()
        val rightContent = (round((wydatek[1].toFloat() * wydatek[2].toFloat()) * 100) / 100F).toString() + "zł"
        var leftContent = wydatek[0]
        if (wydatek[3] != "") {
            leftContent = "$leftContent, " + wydatek[3]
        }

        leftText.text = leftContent
        rightText.text = rightContent
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