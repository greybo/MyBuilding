package com.greybot.mycosts.present.adapter

import androidx.recyclerview.widget.DiffUtil
import com.greybot.mycosts.models.AdapterItems

class ExploreDiffCallback : DiffUtil.ItemCallback<AdapterItems>() {

    //2
    override fun areItemsTheSame(oldItem: AdapterItems, newItem: AdapterItems) =
        when {
            oldItem is AdapterItems.RowItem && newItem is AdapterItems.RowItem -> {
                oldItem.path == newItem.path
            }
            oldItem is AdapterItems.TotalItem && newItem is AdapterItems.TotalItem -> {
                true
            }
            oldItem is AdapterItems.FolderItem && newItem is AdapterItems.FolderItem -> {
                oldItem.name == newItem.name
            }
            oldItem is AdapterItems.ButtonAddItem && newItem is AdapterItems.ButtonAddItem -> {
                oldItem.type == newItem.type
            }
            else -> oldItem::class == newItem::class//false
        }
//    oldItem.id == newItem.id

    //3
    override fun areContentsTheSame(oldItem: AdapterItems, newItem: AdapterItems) =
        when {
            oldItem is AdapterItems.RowItem && newItem is AdapterItems.RowItem -> {
                oldItem.isBought == newItem.isBought &&
                        oldItem.name == newItem.name &&
                        oldItem.path == newItem.path &&
                        oldItem.count == newItem.count &&
                        oldItem.price == newItem.price
            }
            oldItem is AdapterItems.TotalItem && newItem is AdapterItems.TotalItem -> {
                oldItem.value1 == newItem.value1 &&
                        oldItem.value2 == newItem.value2
            }
            oldItem is AdapterItems.FolderItem && newItem is AdapterItems.FolderItem -> {
                oldItem.name == newItem.name &&
                        oldItem.total == newItem.total &&
                        oldItem.countInner == newItem.countInner
            }
            oldItem is AdapterItems.ButtonAddItem && newItem is AdapterItems.ButtonAddItem -> {
                oldItem.type == newItem.type
            }
            else ->false //oldItem::class == newItem::class//
        }
}