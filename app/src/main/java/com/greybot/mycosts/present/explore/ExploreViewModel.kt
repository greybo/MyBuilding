package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.AddFolderUseCases
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.FindFolderUseCases
import com.greybot.mycosts.models.AdapterItems

//TODO init with hilt
class ExploreViewModel() : CompositeViewModel() {

    private val addFolderUseCase get() = AddFolderUseCases()
    private val findUseCase get() = FindFolderUseCases()
    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData() {
        findItems("")
    }

    private fun findItems(path: String) {
        findUseCase.invoke(path) {
            val list = it?.toMutableList() ?: mutableListOf()
            list.add(AdapterItems.ButtonAddItem("Folder"))
            _state.value = list
        }
    }

    fun addFolder(name: String?, path: String?) {
        addFolderUseCase.invoke(name, path)
    }

}



