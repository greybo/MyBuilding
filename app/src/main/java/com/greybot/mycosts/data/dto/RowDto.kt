package com.greybot.mycosts.data.dto

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
)