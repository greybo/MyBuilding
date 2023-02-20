package com.greybot.mycosts.present.adapter

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.greybot.mycosts.components.ItemFileComponent
import com.greybot.mycosts.components.ItemFolderComponent
import com.greybot.mycosts.models.AdapterItems

@Composable
fun AdapterComponent(list: List<AdapterItems>?, callback: (AdapterCallback) -> Unit) {
    val itemsNotNull = list ?: emptyList()
    LazyColumn {
        items(itemsNotNull) { item ->
            when (item) {
                is AdapterItems.FolderItem -> ItemFolderComponent(item, callback)
                is AdapterItems.RowItem -> ItemFileComponent(item, callback)
                else -> throw Throwable()
            }
        }
    }
}