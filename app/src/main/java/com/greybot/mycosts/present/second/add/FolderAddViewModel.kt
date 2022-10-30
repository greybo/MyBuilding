package com.greybot.mycosts.present.second.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.Path
import com.greybot.mycosts.data.dto.Explore
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.ExploreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FolderAddViewModel @Inject constructor(private val exploreRepo: ExploreRepository) :
    CompositeViewModel() {
    var explore: Explore? = null
    var objectId: String = ""
    var path: String = ""

    fun fetchData(objectId: String?, path: String?) {
        this.objectId = objectId ?: ""
        this.path = path ?: ""

        launchOnDefault {
            val folder = exploreRepo.findById(objectId ?: "")
            withMain { explore = folder }
        }
    }

    fun addFolderNew(name: String?) {
        if (name != null) {
            val dto = FileRow(_name = name, path = Path(path).addToPath(name), date = Date().time)
            val explore = explore?.addNewFolder(dto)
            explore?.let {
                exploreRepo.update(it)
            }
        }
    }
}

private fun Explore.addNewFolder(dto: FileRow): Explore {
    val list = buildList {
        addAll(this@addNewFolder.files)
        add(dto)
    }
    return copy(files = list)
}
