package com.greybot.mybuilding.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mybuilding.base.CompositeViewModel
import com.greybot.mybuilding.data.repository.AppRepository

class FolderPreviewViewModel(private val repo: AppRepository = AppRepository()) : CompositeViewModel() {
    private var _state = MutableLiveData<List<String>>()
    val state: LiveData<List<String>> = _state

    fun fetchData(folderName: String?,layer:Int) {
        _state.value = listOf("my building order")
        repo.getAllFolder().
    }

}