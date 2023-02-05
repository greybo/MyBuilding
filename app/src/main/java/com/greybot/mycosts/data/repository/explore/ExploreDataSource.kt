package com.greybot.mycosts.data.repository.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.utility.LogApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreDataSource @Inject constructor(private val repo: ExploreRepository) {
    private val tag = "ExploreDataSource"

    val groupMap = mutableMapOf<String, List<ExploreRow>>()
    private val _listLiveData = MutableLiveData<Map<String, List<ExploreRow>>>()
    private val actor = ExploreActorCoroutine()

    val listLiveData: LiveData<Map<String, List<ExploreRow>>> = Transformations.map(_listLiveData) {
        it
    }

    fun fetchData() {
        repo.getAllData({
            actor.addAll(it)
            groupByParentId(it)
        }, { error ->
            LogApp.e(tag, error)
        })
    }

    private fun groupByParentId(list: List<ExploreRow>) {
        groupMap.clear()
        groupMap.putAll(
            list.groupBy { it.parentObjectId ?: "root" }
        )
        _listLiveData.postValue(groupMap)
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

    fun findByParentId(objectId: String): List<ExploreRow>? {
        return groupMap.getOrNull(objectId)
    }
}

fun <K, V> Map<K, List<V>>.getOrNull(k: K): List<V>? {
    return getOrElse(k) {
        null //emptyList()
    }
}


