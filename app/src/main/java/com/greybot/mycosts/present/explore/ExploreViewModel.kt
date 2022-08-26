package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.folder.preview.formatPathFolder


//TODO init with hilt
class ExploreViewModel : CompositeViewModel() {

    private val folderDataSource by lazy { FolderDataSource() }

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String = "") {
        findItems(path)
    }

    private fun findItems(path: String) {
        launchOnDefault {
            val folders = folderDataSource.findFolder(path)
            makeFolderItems(path, folders)
        }
    }

    private fun makeFolderItems(path: String, map: Map<String?, List<FolderDTO>>?) {
        val list = map?.mapNotNull { entry ->
            entry.key?.let {name->
                AdapterItems.FolderItem(name = name, path = formatPathFolder(path, name), countInner = entry.value.size)
            }
        } ?: emptyList()
        _state.postValue(list)
    }

    fun addFolder(name: String?, path: String?) {
        folderDataSource.addFolder(name, path)
    }

}
