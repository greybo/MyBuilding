package com.greybot.mycosts.data.repository

import com.greybot.mycosts.data.ExploreDataSource
import com.greybot.mycosts.data.dto.ItemFolderDTO
import kotlinx.coroutines.CompletableDeferred

class AppRepository {
    private val sourceExp by lazy { ExploreDataSource() }

    fun getAllFolder(callback: (List<ItemFolderDTO>?) -> Unit) {
        sourceExp.getFolderAll {
            callback(it ?: emptyList())
        }
    }

    suspend fun findFolder(
        path: String?,
        response: CompletableDeferred<List<ItemFolderDTO>?> = CompletableDeferred()
    ): List<ItemFolderDTO>? {
        sourceExp.getFolderAll { list ->
            response.complete(list?.filter { it.path.startsWith(path ?: "") })
        }
        return response.await()
    }

    fun saveNewFolder(name: String, path: String) {
        val item = ItemFolderDTO(name = name, path = path)
        sourceExp.addFolder(item)
    }
}
