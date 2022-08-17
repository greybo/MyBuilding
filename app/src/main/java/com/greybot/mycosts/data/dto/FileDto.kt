package com.greybot.mycosts.data.dto

import java.util.*

data class FileDto(
    val path: String,
    val title: String,
    val count: String?,
    val price: Float?,
    val currency: CurrencyDto?,
    val data: Long = Date().time
)