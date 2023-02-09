package com.greybot.mycosts.data.repository.file

import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.FileRow
import javax.inject.Inject

class FileDataSource @Inject constructor(private val repo: FileRepo) {

    suspend fun fetchData(force: Boolean = false): Map<String, List<FileRow>>? {
        return fileGroup(repo.getAll(force))
    }

    private fun fileGroup(list: List<FileRow>?) = list?.groupBy { it.parentObjectId ?: "empty" }

    suspend fun getListById(parentId: String) =
        repo.getAll().filter { it.parentObjectId == parentId }

    suspend fun findById(
        objectId: String
    ) = repo.getAll().find { dto -> dto.objectId == objectId }

    suspend fun addFile(
        rowName: String,
        count: Float = 0f,
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
        repo.addFile(row)
    }

    suspend fun changeBuyStatus(objectId: String): Boolean {
        val model = findById(objectId) ?: return false
        val changedRow = model.copy(bought = !model.bought)
        return update(changedRow)
    }

    suspend fun update(model: FileRow?): Boolean {
        return model?.let {
            repo.update(it)
        } ?: false
    }

    suspend fun changePrice(objectId: String, count: Float, price: Float) {
        findById(objectId)
            ?.copy(count = count, price = price)
            ?.let { update(it) }
    }

    suspend fun delete(objectId: String) {
        repo.deleteFile(objectId)
    }
}
