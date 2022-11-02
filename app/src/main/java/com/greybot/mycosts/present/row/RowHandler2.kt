package com.greybot.mycosts.present.row

import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.models.MeasureType

class RowHandler2 {

    fun makeGroupBuy(rowList: List<FileRow>): List<AdapterItems> {
        val groups = rowList.groupBy { it.bought }
        return buildList {
            addAll(makeItems(groups[false]))
            addAll(makeItems(groups[true]))
            add(AdapterItems.SpaceItem())
        }
    }

    private fun makeItems(list: List<FileRow>?): List<AdapterItems> {
        list ?: return emptyList()
        val items = mutableListOf<AdapterItems>()
        val todoTotal = list.foldRight(0f) { row, sum ->
            items.add(mapToRowItem(row))
            row.count * row.price + sum
        }
        items.add(AdapterItems.TotalItem(todoTotal))
        return items
    }

    private fun mapToRowItem(item: FileRow) = AdapterItems.RowItem(
        name = item.name,
        path = "",
        measure = MeasureType.toType(item.measure),
        price = item.price,
        count = item.count,
        isBought = item.bought,
    )

}