package com.greybot.mycosts.present.adapter

import com.greybot.mycosts.models.AdapterItems

sealed class AdapterCallback {
    class Name(val value: AdapterItems.RowItem) : AdapterCallback()
    class Price(val value: AdapterItems.RowItem) : AdapterCallback()
    class Buy(val value: AdapterItems.RowItem) : AdapterCallback()
    class Total(val value: AdapterItems.TotalItem) : AdapterCallback()
    class AddButton(val value: AdapterItems.ButtonAddItem) : AdapterCallback()
    class FolderOpen(val value: AdapterItems.FolderItem) : AdapterCallback()
    class FolderLong(val value: AdapterItems.FolderItem) : AdapterCallback()
}
