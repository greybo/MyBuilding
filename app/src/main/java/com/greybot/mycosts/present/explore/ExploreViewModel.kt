package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.folder.preview.FolderHandler


//TODO init with hilt
class ExploreViewModel : CompositeViewModel() {

    private val folderDataSource by lazy { FolderDataSource() }
    private val folderHandler by lazy { FolderHandler() }

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String = "") {
        findItems(path)
    }

    private fun findItems(path: String) {
        launchOnDefault {
            val folders = folderDataSource.findFolder(path)
            val items = folderHandler.makeFolderItems(path, folders)
            _state.postValue(items)
        }
    }

    fun addFolder(name: String?, path: String?) {
        folderDataSource.addFolder(name, path)
    }

}
