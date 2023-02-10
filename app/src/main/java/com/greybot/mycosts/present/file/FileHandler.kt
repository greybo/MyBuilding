package com.greybot.mycosts.present.file

import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.models.MeasureType
import com.greybot.mycosts.present.folder.preview.ButtonType
import com.greybot.mycosts.utility.round2String

class FileHandler {

    fun makeGroupBuy(rowList: List<FileRow>): List<AdapterItems> {
        val groups = rowList.groupBy { it.bought }
        val pairOrder = makeItems(groups[false])
        val pairCheck = makeItems(groups[true])
        return buildList {
            addAll(pairOrder.first)
            add(
                AdapterItems.TotalItem(
                    value1 = pairOrder.second,
                    value2 = pairCheck.second
                )
            )
            addAll(pairCheck.first)
            add(AdapterItems.ButtonAddItem(ButtonType.Row))
            add(AdapterItems.SpaceItem())
        }
    }

    private fun makeItems(list: List<FileRow>?): Pair<List<AdapterItems>, String> {
        list ?: return Pair(emptyList(), "0.0")
        val items = mutableListOf<AdapterItems>()
        val total = list.foldRight(0.0) { row, sum ->
            items.add(mapToRowItem(row))
            row.count * row.price + sum
        }
        return Pair(items, total.round2String())
    }

    private fun mapToRowItem(item: FileRow) = AdapterItems.RowItem(
        name = item.name,
        path = "",
        measure = MeasureType.toType(item.measure),
        count = item.count,
        price = item.price,
        isBought = item.bought,
        objectId = item.objectId ?: ""
    )

}