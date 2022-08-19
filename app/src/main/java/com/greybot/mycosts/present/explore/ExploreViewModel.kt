package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.FindFolderUseCases
import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.addToPath
import com.greybot.mycosts.utility.toast

//TODO init with hilt
class ExploreViewModel(private val repo: AppRepository = AppRepository()) : CompositeViewModel() {

    private val findUseCase get() = FindFolderUseCases(repo)
    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData() {
        findItems("")
    }

    private fun findItems(path: String) {
        findUseCase.invoke(path) {
            val list = it.toMutableList()
            list.add(AdapterItems.AddContentItem("Folder"))
            _state.value = list
        }
    }

    fun addFolder(name: String?, path: String?) {
        if (!name.isNullOrBlank()) {
            repo.saveNewFolder(name, path.addToPath(name))
        } else toast("name null")
    }

}



