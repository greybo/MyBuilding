package com.greybot.mycosts.present.adapter

import com.greybot.mycosts.models.AdapterItems

interface IRowCost{
    val value: AdapterItems.RowItem
}
sealed class AdapterCallback {
    class RowName(val value: AdapterItems.RowItem) : AdapterCallback()
    class RowPrice(override val value: AdapterItems.RowItem) : AdapterCallback(),IRowCost
    class RowCount(override val value: AdapterItems.RowItem) : AdapterCallback(),IRowCost
    class RowBuy(val value: AdapterItems.RowItem) : AdapterCallback()
    class Total(val value: AdapterItems.TotalItem) : AdapterCallback()
    class AddButton(val value: AdapterItems.ButtonAddItem) : AdapterCallback()
    class FolderOpen(val value: AdapterItems.FolderItem) : AdapterCallback()
    class FolderHighlight(val value: AdapterItems.FolderItem) : AdapterCallback()
    class FileHighlight(val value: AdapterItems.RowItem) : AdapterCallback()
    class FolderAdd(val id: String) : AdapterCallback()
}
