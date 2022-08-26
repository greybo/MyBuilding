package com.greybot.mycosts.data.repository.folder

import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.utility.addToPath
import com.greybot.mycosts.utility.toast
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FolderDataSource(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)
    private val repo: FolderRepo = FolderRepo()

    suspend fun findFolder(
        path: String
    ): Map<String?, List<FolderDTO>>? {
        val findPath = "$path/"
        return repo.getFolderAll()?.filter { it.path.startsWith(findPath) }
            ?.groupBy {
                it.path.substring(findPath.length, it.path.length).split("/").getOrNull(0)
            }

//        return list?.mapNotNull { item ->
//            if (item.path.startsWith(findPath)) {
//                val name = getNameFromPath(findPath, item.path)
//                AdapterItems.FolderItem(name, "$findPath$name")
//            } else null
//        }?.toMutableSet()
    }

    fun addFolder(name: String?, path: String?) {
        if (!name.isNullOrBlank()) {
            val item = FolderDTO(name = name, path = path.addToPath(name))
            repo.addFolder(item)
        } else toast("name null")
    }
}
