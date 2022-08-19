package com.greybot.mycosts.present.file.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.RowDataSource
import com.greybot.mycosts.utility.LogApp

class RowAddViewModel : CompositeViewModel() {

    //    private val addUseCase = AddRowUseCases(repo)
    private val source: RowDataSource by lazy { RowDataSource() }

    fun addRow(
        path: String,
        rowName: String,
        count: String = "1",
        price: Float = 0F,
        currency: CurrencyDto? = null
    ) {
        var _count = try {
            count.toInt()
        } catch (e: Exception) {
            LogApp.w("addRow_tag", e)
            1
        }
        if (_count == 0) {
            _count = 1
        }
        val row = RowDto(
            path = path,
            title = rowName,
            count = _count,
            price = price,
            currency = currency
        )
        source.addRow(row)
    }
}