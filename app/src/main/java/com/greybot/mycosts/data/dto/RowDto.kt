package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import java.util.*

data class RowDto(
    var objectId: String? = null,
    val path: String = "",
    val title: String = "",
    val measure: String = "",
    val count: Int = 1,
    val price: Float = 0F,
    val isBought: Boolean = false,
    val currency: CurrencyDto? = CurrencyDto(),
    val data: Long = Date().time
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "objectId" to objectId,
            "path" to path,
            "title" to title,
            "measure" to measure,
            "count" to count,
            "price" to price,
            "isBought" to isBought,
            "currency" to currency,
            "data" to data,
        )
    }
}