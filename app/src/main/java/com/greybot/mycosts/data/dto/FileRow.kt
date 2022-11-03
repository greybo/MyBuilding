package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import com.google.gson.annotations.SerializedName
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

interface IFolder {

}

data class FileRow(
    @SerializedName("Name")
    override val name: String = "",
    @SerializedName("Measure")
    override val measure: String? = null,
    @SerializedName("Count")
    override val count: Int = 1,
    @SerializedName("Price")
    override val price: Float = 0F,
    @SerializedName("Bought")
    override val bought: Boolean = false,
    @SerializedName("Currency")
    override val currency: CurrencyDto? = CurrencyDto(),
    @SerializedName("Data")
    override val date: Long = Date().time,
    @SerializedName("ObjectId")
    var objectId: String? = null,
    @SerializedName("ParentObjectId")
    var parentObjectId: String? = null,
) : IFile, IFolder {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Name" to name,
            "Measure" to measure,
            "Count" to count,
            "Price" to price,
            "Bought" to bought,
            "Currency" to currency,
            "Data" to date,
            "ObjectId" to objectId,
            "ParentObjectId" to parentObjectId,
        )
    }
}

