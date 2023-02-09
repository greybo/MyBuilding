package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

interface IFile {
    val name: String
    val measure: String?
    val count: Int
    val price: Float
    val bought: Boolean
    val currency: CurrencyDto?
    val date: Long
}

interface IFolder

@IgnoreExtraProperties
data class FileRow(
    override val name: String = "",
    override val measure: String? = null,
    override val count: Int = 1,
    override val price: Float = 0F,
    override val bought: Boolean = false,
    override val currency: CurrencyDto? = CurrencyDto(),
    override val date: Long = Date().time,
    var objectId: String? = null,
    var parentObjectId: String? = null,
) : IFile, IFolder {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "measure" to measure,
            "count" to count,
            "price" to price,
            "bought" to bought,
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

