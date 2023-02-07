package com.greybot.mycosts.present.second

import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.second.preview.ButtonType
import com.greybot.mycosts.present.second.preview.ItemTotalHelper
import com.greybot.mycosts.utility.getTotalString

class FolderHandler(private val totalHelper: ItemTotalHelper) {
    fun makeFolderItems(list: List<ExploreRow>): List<AdapterItems> {
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
