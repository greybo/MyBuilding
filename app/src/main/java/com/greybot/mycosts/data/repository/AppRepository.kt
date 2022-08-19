package com.greybot.mycosts.data.repository

import com.greybot.mycosts.data.dto.FolderDTO

class AppRepository {
    private val sourceExp by lazy { FolderDataSource() }
    private val sourceRow by lazy { RowDataSource() }

    fun getAllFolder(callback: (List<FolderDTO>?) -> Unit) {
        sourceExp.getFolderAll {
            callback(it ?: emptyList())
        }
    }

//    suspend fun findFolder(
//        path: String?,
//        response: CompletableDeferred<List<ItemFolderDTO>?> = CompletableDeferred()
//    ): List<ItemFolderDTO>? {
//        sourceExp.getFolderAll { list ->
//            response.complete(list?.filter { it.path.startsWith(path ?: "") })
//        }
//        return response.await()
//    }

    fun addNewFolder(name: String, path: String) {
        val item = FolderDTO(name = name, path = path)
        sourceExp.addFolder(item)
    }
}
