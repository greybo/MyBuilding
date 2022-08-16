package com.greybot.mybuilding.ui.folder.preview

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mybuilding.base.CompositeViewModel

class FolderPreviewViewModel : CompositeViewModel() {
    val args: FolderPreviewFragmentArgs? = null
    private var _state = MutableLiveData<List<String>>()
    val state: LiveData<List<String>> = _state

    fun fetchData() {
        _state.value = listOf("my building order")
    }

    fun args(arguments: Bundle?) {
        val args by lazy { arguments?.let { FolderPreviewFragmentArgs } }

    }
}