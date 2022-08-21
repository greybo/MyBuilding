package com.greybot.mycosts.models

import com.greybot.mycosts.present.folder.preview.ButtonType

sealed class AdapterItems {
    class FolderItem(val name: String, val path: String, val description: String? = null) :
        AdapterItems() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as FolderItem

            if (name != other.name) return false
            if (path != other.path) return false
            if (description != other.description) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + path.hashCode()
            result = 31 * result + (description?.hashCode() ?: 0)
            return result
        }
    }

    class RowItem(
        val name: String,
        val path: String,
        val price: Float = 0F,
        val objectId: String,
    ) : AdapterItems()

    class ButtonAddItem(val type: ButtonType) : AdapterItems()
    class TotalItem(val value: Float, val name: String = "Total") : AdapterItems()
}
