package com.greybot.mycosts.present.folder.preview

import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.folder.getOrNull
import com.greybot.mycosts.utility.LogApp

class ItemTotalHelper(private var folderGroup: Map<String, List<FolderRow>>?, private var fileGroup:  Map<String, List<FileRow>>?) {

    fun getTotalById(id: String): ItemTotalModel {
        val totalFolder = folderGroup?.getOrNull(id)
        var count: Int? = null
        var price: Float? = null
        if (!totalFolder.isNullOrEmpty()) {
            count = totalFolder.size
        } else {
            fileGroup?.getOrNull(id)?.let { rowList ->
                LogApp.i("ItemTotalHelper.getTotalById() - size: ${rowList.size}, $rowList" )
                count = rowList.size
                price = rowList.foldRight(0F) { row, sum ->
                    row.count.getNotNull() * row.price + sum
                }
            }
        }
        return ItemTotalModel(count, price)
    }
}

private fun Int?.getNotNull(): Int {
    return if (this == null || this == 0) 1 else this
}

data class ItemTotalModel(private val count: Int? = null, private val price: Float? = null) {
    val totalCount: String = count?.toString() ?: ""
    val totalPrice: String = price?.toString() ?: ""
}