package com.example.aplikacja

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

internal class CardViewListAdapter : ListAdapter<CardViewItem, CardViewHolder>(
    ItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder.create(parent)

    override fun onBindViewHolder(holder: CardViewHolder, position: Int): Unit =
        holder.bindModelToView(getItem(position))
}

private object ItemDiffCallback : DiffUtil.ItemCallback<CardViewItem>() {

    override fun areItemsTheSame(oldItem: CardViewItem, newItem: CardViewItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CardViewItem, newItem: CardViewItem): Boolean =
        oldItem == newItem
}