package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

interface IFolder {
    val name: String
    val measure: String?
    var count: String
    var price: String
    val bought: Boolean
    val currency: CurrencyDto?
    val date: Long
    var objectId: String?
    var parentObjectId: String?
}

data class FileDto(
    val name: String? = null,
    val measure: String? = null,
    var count: String? = null,
    var price: String? = null,
    val bought: Boolean? = null,
    val currency: CurrencyDto? = null,
    val date: Long  = Date().time,
    var objectId: String? = null,
    var parentObjectId: String? = null,
) {

    fun getModel() = FileRow(
        name = name ?: "",
        measure = measure,
        count = (count ?: "1.0").toFloat(),
        price = (price ?: "0.0").toFloat(),
        bought = bought ?: false,
        currency = currency,
        date = date ,
        objectId = objectId,
        parentObjectId = parentObjectId
    )
}

@IgnoreExtraProperties
data class FileRow(
    val name: String = "",
    val measure: String? = null,
    var count: Float = 1f,
    var price: Float = 0f,
    val bought: Boolean = false,
    val currency: CurrencyDto? = CurrencyDto(),
    val date: Long = Date().time,
    var objectId: String? = null,
    var parentObjectId: String? = null,
) {
    fun getDto() = FileDto(
        name,
        measure,
        count.toString(),
        price.toString(),
        bought,
        currency,
        date,
        objectId,
        parentObjectId
    )

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "measure" to measure,
            "count" to count,
            "price" to price.toString(),
            "bought" to bought.toString(),
            "currency" to currency,
            "data" to date,
            "objectId" to objectId,
            "parentObjectId" to parentObjectId,
        )
    }

    override fun toString(): String {
        return "FileRow(" +
                "name='$name', " +
//                "measure=$measure, " +
//                "count=$count, " +
//                "price=$price, " +
//                "bought=$bought, " +
//                "currency=$currency, " +
//                "date=$date, " +
                "objectId=$objectId, " +
//                "parentObjectId=$parentObjectId" +
                ")"
    }
}

