package com.greybot.mybuilding.ui.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mybuilding.base.CompositeViewModel

class FolderPreviewViewModel : CompositeViewModel() {

    private var _state = MutableLiveData<List<String>>()
    val state: LiveData<List<String>> = _state

    fun fetchData() {
        _state.value = listOf("my building order")
    }
}