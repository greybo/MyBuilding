package com.greybot.mycosts.models

sealed class AdapterItems {
    class FolderItem(val name: String, val path: String, val description: String? = null) :
        AdapterItems()

    class RowItem(val name: String, val path: String, val price: Float = 0F) : AdapterItems()
    class ButtonAddItem(val name: String) : AdapterItems()
}
