package com.greybot.mycosts.data.repository.explore

import com.greybot.mycosts.data.dto.ExploreRow
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

//@Singleton
class ExploreDataSource @Inject constructor(private val repo: ExploreRepository) {

    fun groupByParentId(): Map<String?, List<ExploreRow>> {
        return repo.backupList.groupBy { it.parentObjectId }
    }

    suspend fun getRootFolder(): List<ExploreRow>? {
        return getALLExplore()?.filter { it.parentObjectId.isNullOrEmpty() }
    }

    suspend fun getALLExplore(): List<ExploreRow>? {
        val deferred = CompletableDeferred<List<ExploreRow>?>()
        if (repo.backupList.isNotEmpty()) {
            deferred.complete(repo.backupList)
        } else
            repo.getRemoteAll({
                deferred.complete(it)
                repo.backupList.clear()
                repo.backupList.addAll(it)
            }, { error ->
                deferred.completeExceptionally(error)
            })
        return deferred.await()
    }

    fun findChildren(objectId: String): List<ExploreRow> {
        return groupByParentId().getOrElse(objectId) {
            emptyList()
        }
    }

    fun addFolder(model: ExploreRow) {
        repo.addFolder(model)
    }

    fun updateFolder(model: ExploreRow) {
        repo.update(model)
    }

    fun findParent(objectId: String): ExploreRow? {
        return repo.backupList.find { it.objectId == objectId }
    }

    fun findFolderModels(objectId: String): FolderModels {
        val parent = findParent(objectId)
        val children = if (parent.isFolder) {
            findChildren(objectId)
        } else emptyList()
        return FolderModels(parent, children)
    }

    private val ExploreRow?.isFolder: Boolean
        get() = !(this?.isFiles ?: true)
}

data class FolderModels(val parent: ExploreRow?, val children: List<ExploreRow>) {
    fun ifFiles(): Boolean {
        return parent?.isFiles ?: false
    }
}




