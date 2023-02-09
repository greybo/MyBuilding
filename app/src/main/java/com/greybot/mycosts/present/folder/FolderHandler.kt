package com.greybot.mycosts.present.folder

import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.folder.preview.ButtonType
import com.greybot.mycosts.present.folder.preview.ItemTotalHelper
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.getTotalString

class FolderHandler(
    private val folderGroup: Map<String, List<FolderRow>>,
    private val fileGroup: Map<String, List<FileRow>>?
) {
     fun makeFolderItems(list: List<FolderRow>): List<AdapterItems> {
         LogApp.i(
             "FolderHandler.makeFolderItems() - size: ${fileGroup?.size}, $fileGroup"
         )
        val totalHelper = ItemTotalHelper(folderGroup, fileGroup)
        return buildList {
            addAll(list.map { f ->
                val totalData = totalHelper.getTotalById(f.objectId ?: "")
                AdapterItems.FolderItem(
                    f.name ?: "null",
                    "",
                    countInner = totalData.totalCount.getTotalString("count"),
                    total = totalData.totalPrice.getTotalString("total"),
                    objectId = f.objectId
                )
            })
            add(AdapterItems.ButtonAddItem(ButtonType.Folder))
        }
    }
}
