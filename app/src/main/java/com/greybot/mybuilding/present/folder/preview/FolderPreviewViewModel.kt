package com.greybot.mybuilding.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mybuilding.base.CompositeViewModel
import com.greybot.mybuilding.data.repository.AppRepository

class FolderPreviewViewModel(private val repo: AppRepository = AppRepository()) :
    CompositeViewModel() {

    private var _state = MutableLiveData<List<String>>()
    val state: LiveData<List<String>> = _state

    fun fetchData(path: String?) {
        val layer: Int = path?.split("/")?.filter { it.isNotBlank() }?.size ?: 0
        _state.value = repo.findFolder(path).map {
            it.split("/")[layer]
        }
    }

}