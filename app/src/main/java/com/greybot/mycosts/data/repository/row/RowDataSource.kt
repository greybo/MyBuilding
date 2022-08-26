package com.greybot.mycosts.data.repository.row

import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.RowDto

class RowDataSource {
    private val repo: RowRepo = RowRepo()

    suspend fun findByPath(
        findPath: String
    ) = repo.getAllData()?.filter { it.path.startsWith(findPath) }


    suspend fun findById(
        objectId: String
    ) = repo.getAllData()?.find { dto -> dto.objectId == objectId }

    fun addRow(
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
        repo.addRow(row)
    }
}
