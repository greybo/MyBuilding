package com.greybot.mycosts.present.folder.preview

import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.folder.getOrNull
import com.greybot.mycosts.utility.LogApp

class ItemTotalHelper(private var folderGroup: Map<String, List<FolderRow>>?, private var fileGroup:  Map<String, List<FileRow>>?) {

    fun getTotalById(id: String): ItemTotalModel {
        val totalFolder = folderGroup?.getOrNull(id)
        var countFolder: Int? = null
        var priceTotal: Double? = null
        if (!totalFolder.isNullOrEmpty()) {
            countFolder = totalFolder.size
        } else {
            fileGroup?.getOrNull(id)?.let { rowList ->
                LogApp.i("ItemTotalHelper.getTotalById() - size: ${rowList.size}, $rowList" )
                countFolder = rowList.size
                priceTotal = rowList.foldRight(0.0) { row, sum ->
                    row.count.getNotNull() * row.price + sum
                }
            }
        }
        return ItemTotalModel(countFolder, priceTotal)
    }
}

private fun Double?.getNotNull(): Double {
    return if (this == null || this == 0.0) 1.0 else this
}

data class ItemTotalModel(private val count: Int? = null, private val price: Double? = null) {
    val totalCount: String = count?.toString() ?: ""
    val totalPrice: String = price?.toString() ?: "" //.round2()//
}