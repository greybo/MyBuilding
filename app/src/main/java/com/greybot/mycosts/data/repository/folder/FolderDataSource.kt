package com.greybot.mycosts.data.repository.folder

import com.greybot.mycosts.data.dto.FolderRow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderDataSource @Inject constructor(private val repo: FolderRepository) {
    private val tag = "FolderDataSource"

    private val actor = FolderActor()

    suspend fun fetchData(force: Boolean = false): Map<String, List<FolderRow>> {
        return if (!force) {
            groupByParentId(actor.getAll())
        } else {
            val list = repo.getAllData()
            list?.let { actor.addAll(it) }
            groupByParentId(list)
        }
    }

    private fun groupByParentId(list: List<FolderRow>?): Map<String, List<FolderRow>> {
        return list?.groupBy { it.parentObjectId ?: "root" } ?: emptyMap()
    }

    suspend fun addFolder(model: FolderRow) {
        repo.addFolder(model)?.let {
            actor.add(it)
            groupByParentId(actor.getAll())
        }
    }

    suspend fun updateFolder(model: FolderRow) {
        repo.update(model)
        groupByParentId(actor.update(model))
    }

    suspend fun findByObjectId(objectId: String): FolderRow? {
        return actor.getAll().find { it.objectId == objectId }
    }

    suspend fun getListById(parentId: String): List<FolderRow>? {
        return groupByParentId(actor.getAll()).getOrNull(parentId)
    }
}

fun <K, V> Map<K, List<V>>.getOrNull(k: K): List<V>? {
    return getOrElse(k) {
        null //emptyList()
    }
}


