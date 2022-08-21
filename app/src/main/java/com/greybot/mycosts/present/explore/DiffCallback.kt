package com.greybot.mycosts.present.explore

import androidx.recyclerview.widget.DiffUtil
import com.greybot.mycosts.models.AdapterItems

class DiffCallback : DiffUtil.ItemCallback<AdapterItems>() {

    //2
    override fun areItemsTheSame(oldItem: AdapterItems, newItem: AdapterItems) =
        when {
            oldItem is AdapterItems.RowItem && newItem is AdapterItems.RowItem -> {
                oldItem.objectId == newItem.objectId
            }
            oldItem is AdapterItems.TotalItem && newItem is AdapterItems.TotalItem -> {
                true
            }
            else -> false//oldItem::class == newItem::class//
        }
//    oldItem.id == newItem.id

    //3
    override fun areContentsTheSame(oldItem: AdapterItems, newItem: AdapterItems) =
        when {
            oldItem is AdapterItems.RowItem && newItem is AdapterItems.RowItem -> {
                oldItem.check == newItem.check &&
                        oldItem.name == newItem.name &&
                        oldItem.path == newItem.path &&
                        oldItem.price == newItem.price
            }
            oldItem is AdapterItems.TotalItem && newItem is AdapterItems.TotalItem -> {
                oldItem.value == newItem.value
            }
            else -> oldItem::class == newItem::class//false
        }
}