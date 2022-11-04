package com.greybot.mycosts.present.file

//import com.greybot.mycosts.data.dto.RowDto
//import com.greybot.mycosts.models.AdapterItems
//import com.greybot.mycosts.models.MeasureType
//
//class RowHandler {
//
//    fun makeGroupBuy(rowList: List<RowDto>): List<AdapterItems> {
//        val groups = rowList.groupBy { it.bought }
//        return buildList {
//            addAll(makeItems(groups[false]))
//            addAll(makeItems(groups[true]))
//            add(AdapterItems.SpaceItem())
//        }
//    }
//
//    private fun makeItems(list: List<RowDto>?): List<AdapterItems> {
//        list ?: return emptyList()
//        val items = mutableListOf<AdapterItems>()
//        val todoTotal = list.foldRight(0f) { row, sum ->
//            items.add(mapToRowItem(row))
//            row.count * row.price + sum
//        }
//        items.add(AdapterItems.TotalItem(todoTotal))
//        return items
//    }
//
//    private fun mapToRowItem(item: RowDto) = AdapterItems.RowItem(
//        name = item.title,
//        path = item.path,
//        measure = MeasureType.toType(item.measure),
//        price = item.price,
//        count = item.count,
//        isBought = item.bought,
//        parentObjectId = item.objectId!!,
//    )
//
//}