package com.greybot.mycosts.data.repository.row

import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.FileRow
import javax.inject.Inject

class FileDataSource @Inject constructor(private val repo: RowRepo) {

    fun geBackupList() = repo.backupList

    suspend fun getAllByParent(
        parentObjectId: String
    ) = repo.getAll().filter { it.parentObjectId == parentObjectId }


    suspend fun findById(
        objectId: String
    ) = repo.getAll().find { dto -> dto.objectId == objectId }

    fun addRow(
        path: String,
        rowName: String,
        count: Int = 0,
        price: Float = 0F,
        currency: CurrencyDto? = null,
        parentId: String?
    ) {
        val row = FileRow(
            name = rowName,
            count = count,
            price = price,
            currency = currency,
            parentObjectId = parentId
        )
        repo.addRow(row)
    }

    fun changeBuyStatus(objectId: String) {
        val list = geBackupList().map { row ->
            if (row.objectId == objectId) {
                val changedRow = row.copy(bought = !row.bought)
                repo.saveModel(changedRow)
                changedRow
            } else row
        }
        repo.saveBackupList(list)
    }

    fun save(model: FileRow?) {
        model?.let { repo.saveModel(it) }
    }
}
