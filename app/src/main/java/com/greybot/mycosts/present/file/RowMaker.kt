package com.greybot.mycosts.present.file

import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.file.edit.mapToRowItem

class RowMaker {
}

fun getTotalPrice(itemList: List<RowDto>) =
    itemList.fold(0F) { t, item ->
        t + item.price
    }

fun List<RowDto>.toRowItem(): List<AdapterItems> {
    return this.map { mapToRowItem(it) }
}

fun MutableList<AdapterItems>.addRowItems(list: List<RowDto>?) {
    list ?: return
    val todoTotal = getTotalPrice(list)
    addAll(list.toRowItem())
    add(AdapterItems.TotalItem(todoTotal))
}