package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import java.util.*

data class RowDto(
    var objectId: String? = null,
    var parentId: String? = null,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RowDto

        if (objectId != other.objectId) return false
        if (parentId != other.parentId) return false
        if (path != other.path) return false
        if (title != other.title) return false
        if (measure != other.measure) return false
        if (count != other.count) return false
        if (price != other.price) return false
        if (isBought != other.isBought) return false
        if (currency != other.currency) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = objectId?.hashCode() ?: 0
        result = 31 * result + (parentId?.hashCode() ?: 0)
        result = 31 * result + path.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + measure.hashCode()
        result = 31 * result + count
        result = 31 * result + price.hashCode()
        result = 31 * result + isBought.hashCode()
        result = 31 * result + (currency?.hashCode() ?: 0)
        result = 31 * result + data.hashCode()
        return result
    }

}