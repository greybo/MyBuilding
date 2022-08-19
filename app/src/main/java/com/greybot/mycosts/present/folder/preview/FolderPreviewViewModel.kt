package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.AddFolderUseCases
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.FindFolderUseCases
import com.greybot.mycosts.models.AdapterItems

class FolderPreviewViewModel() :
    CompositeViewModel() {

    private val addFolderUseCase get() = AddFolderUseCases()
    private val findUseCase get() = FindFolderUseCases()
    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String?) {
        path ?: return
        findUseCase.invoke(path) {
            val list = it?.toMutableList()?.addButton() ?: emptyList()
            _state.postValue(list)
        }
    }

    private fun MutableList<AdapterItems>.addButton(): List<AdapterItems> {
        if (this.isEmpty()) {
            add(AdapterItems.ButtonAddItem("Folder"))
            add(AdapterItems.ButtonAddItem("Row"))
        } else
            if (this[0] is AdapterItems.FolderItem) {
                add(AdapterItems.ButtonAddItem("Folder"))
            } else
                add(AdapterItems.ButtonAddItem("Row"))

        return this
    }

    fun addFolder(name: String?, path: String?) {
        addFolderUseCase.invoke(name, path)
    }

}