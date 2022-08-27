package com.greybot.mycosts.data.repository.folder

import com.greybot.mycosts.base.addToPath
import com.greybot.mycosts.base.findName
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.utility.toast
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FolderDataSource(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)
    private val repo: FolderRepo = FolderRepo()

    suspend fun findFolder(
        pathFind: String
    ): Map<String?, List<FolderDTO>>? {
        val _findPath = "$pathFind/"
        return repo.getFolderAll()?.findByPath(_findPath)
            ?.groupBy {
                findName(_findPath, it.path)
            }
    }

    private fun List<FolderDTO>.findByPath(_findPath: String): List<FolderDTO> {
        return filter { it.path.startsWith(_findPath) }
    }

    fun addFolder(name: String?, path: String?, time: Long) {
        if (!name.isNullOrBlank()) {
            val item = FolderDTO(name = name, path = path.addToPath(name), date = time)
            repo.addFolder(item)
        } else toast("name null")
    }

    fun update(objectId: String, name: String, path: String) {
        repo.update(FolderDTO(objectId = objectId, path = path, name = name))
    }
}
