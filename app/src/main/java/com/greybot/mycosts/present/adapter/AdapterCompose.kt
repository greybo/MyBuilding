package com.greybot.mycosts.present.adapter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.greybot.mycosts.components.items.*
import com.greybot.mycosts.models.AdapterItems

@Composable
fun AdapterComponent(
    list: List<AdapterItems>?,
    isDelete: Boolean,
    callback: (AdapterCallback) -> Unit
) {
    val itemsNotNull = list ?: emptyList()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(itemsNotNull) { item ->
            when (item) {
                is AdapterItems.FolderItem -> ItemFolderComponent(item, callback)
                is AdapterItems.RowItem -> ItemFileComponent(item, isDelete,callback)
                is AdapterItems.ButtonAddItem -> ItemButtonsComponent(item, callback)

                is AdapterItems.TotalItem -> ItemFileTotalComponent(item)
                is AdapterItems.SpaceItem -> ItemSpaceComponent(item)
                else -> throw Throwable()
            }
        }
    }
}