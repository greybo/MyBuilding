package com.greybot.mycosts.data.repository.explore

import com.greybot.mycosts.data.dto.ExploreRow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreDataSource @Inject constructor(private val repo: ExploreRepository) {
    private val tag = "ExploreDataSource"

    private val actor = ExploreActorCoroutine()

    suspend fun fetchData(force: Boolean = false): Map<String, List<ExploreRow>> {
        return if (!force) {
            groupByParentId(actor.getAll())
        } else {
            val list = repo.getAllData()
            list?.let { actor.addAll(it) }
            groupByParentId(list)
        }
    }

    private fun groupByParentId(list: List<ExploreRow>?): Map<String, List<ExploreRow>> {
        return list?.groupBy { it.parentObjectId ?: "root" } ?: emptyMap()
    }

    suspend fun addFolder(model: ExploreRow) {
        repo.addFolder(model)?.let {
            actor.add(it)
            groupByParentId(actor.getAll())
        }
    }

    suspend fun updateFolder(model: ExploreRow) {
        repo.update(model)
        groupByParentId(actor.update(model))
    }

    suspend fun findByObjectId(objectId: String): ExploreRow? {
        return actor.getAll().find { it.objectId == objectId }
    }

    suspend fun getCurrentList(parentId: String): List<ExploreRow>? {
        return groupByParentId(actor.getAll()).getOrNull(parentId)
    }
}

fun <K, V> Map<K, List<V>>.getOrNull(k: K): List<V>? {
    return getOrElse(k) {
        null //emptyList()
    }
}


