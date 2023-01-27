package com.greybot.mycosts.data.repository.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.greybot.mycosts.data.dto.ExploreRow
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreDataSource @Inject constructor(private val repo: ExploreRepository) {

    private val backupList = mutableListOf<ExploreRow>()
    private val _listLiveData = MutableLiveData<Map<String?, List<ExploreRow>>>()
    private val actor = ExploreActorCoroutine()

    //    val listLiveData: LiveData<Map<String?, List<ExploreRow>>> = _listLiveData
    val listLiveData = Transformations.map(_listLiveData) {
        it
    }

    fun fetchData() {
//        actor.add()
    }

    suspend fun getRootFolder(): List<ExploreRow>? {
        return fetch()?.filter { it.parentObjectId.isNullOrEmpty() }
    }

    fun groupByParentId(): Map<String?, List<ExploreRow>> {
        return backupList.groupBy { it.parentObjectId }
    }

    suspend fun groupByParentId2() {
        return _listLiveData.postValue(fetch()?.groupBy { it.parentObjectId ?: "root" }
            ?: emptyMap())
    }

    private suspend fun fetch(): List<ExploreRow>? {
        val deferred = CompletableDeferred<List<ExploreRow>?>()
        if (backupList.isNotEmpty()) {
            deferred.complete(backupList)
        } else
            repo.getAllData({
                actor.addAll(it)
                deferred.complete(it)
            }, { error ->
                deferred.completeExceptionally(error)
            })
        return deferred.await()
    }

     fun addFolder(model: ExploreRow) {
        repo.addFolder(model)?.let {
            backupList.add(it)
        }
    }

    fun updateFolder(model: ExploreRow) {
        repo.update(model)
        backupList.forEachIndexed { index, item ->
            if (model.objectId == item.objectId) {
                backupList[index] = model
            }
        }
    }

    fun findParent(objectId: String): ExploreRow? {
        return backupList.find { it.objectId == objectId }
    }

    fun findFolderModels(objectId: String): FolderModels {
        val parent = findParent(objectId)
        val children = if (parent.isFolder) {
            findChildren(objectId)
        } else emptyList()
        return FolderModels(parent, children)
    }

    private fun findChildren(objectId: String): List<ExploreRow> {
        return groupByParentId().getOrElse(objectId) {
            emptyList()
        }
    }

    private val ExploreRow?.isFolder: Boolean
        get() = !(this?.files ?: true)
}

data class FolderModels(val parent: ExploreRow?, val children: List<ExploreRow>) {
    fun ifFiles(): Boolean {
        return parent?.files ?: false
    }
}




