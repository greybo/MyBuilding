package com.greybot.mycosts.base

import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.RowDataSource

class AddRowUseCases(private val source: RowDataSource = RowDataSource()) {

    operator fun invoke(
        path: String,
        rowName: String,
        count: Int = 0,
        price: Float = 0F,
        currency: CurrencyDto? = null
    ) {
        val row = RowDto(
            path = path,
            title = rowName,
            count = count,
            price = price,
            currency = currency
        )
        source.addRow(row)
    }
}