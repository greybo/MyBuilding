package com.greybot.mycosts.present.file

import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.models.MeasureType

fun getTotalPrice(itemList: List<RowDto>) =
    itemList.fold(0F) { t, item ->
        t + item.price
    }

fun MutableList<AdapterItems>.addRowItems(list: List<RowDto>?) {
    list ?: return
    val todoTotal = getTotalPrice(list)
    addAll(list.toRowItem())
    add(AdapterItems.TotalItem(todoTotal))
}

fun List<RowDto>.toRowItem(): List<AdapterItems> {
    return this.map { mapToRowItem(it) }
}

fun mapToRowItem(item: RowDto) = AdapterItems.RowItem(
    name = item.title,
    path = item.path,
    measure = MeasureType.toType(item.measure),
    price = item.price,
    count = item.count,
    isBought = item.isBought,
    objectId = item.objectId!!,
)