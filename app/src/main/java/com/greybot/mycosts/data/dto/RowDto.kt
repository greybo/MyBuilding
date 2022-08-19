package com.greybot.mycosts.data.dto

import java.util.*

data class RowDto(
    var objectId: String? = null,
    val path: String,
    val title: String,
    val count: Int?,
    val price: Float?,
    val currency: CurrencyDto?,
    val data: Long = Date().time
)