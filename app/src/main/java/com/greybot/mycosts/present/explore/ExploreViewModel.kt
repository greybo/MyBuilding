package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.data.repository.row.RowDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.formatPathFolder


//TODO init with hilt
class ExploreViewModel : CompositeViewModel() {

    private val folderDataSource by lazy { FolderDataSource() }
    private val rowDataSource by lazy { RowDataSource() }

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String = "") {
        findItems(path)
    }

    private fun findItems(path: String) {
        launchOnDefault {
            val folders = folderDataSource.findFolder(path)
            makeFolderItems(folders)
        }
    }

    private fun makeFolderItems(map: Map<String?, List<FolderDTO>>?) {
        val list = map?.mapNotNull { entry ->
            entry.key?.let {
                AdapterItems.FolderItem(name = it, path = it.formatPathFolder(), countInner = entry.value.size)
            }
        } ?: emptyList()
        _state.postValue(list)
    }

    private suspend fun findRows(list: MutableList<AdapterItems.FolderItem>) {
        list.map {
            rowDataSource.findByPath(it.path)
        }
    }

    fun addFolder(name: String?, path: String?) {
        folderDataSource.addFolder(name, path)
    }

}
