package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.models.ExploreItem
import com.greybot.mycosts.utility.splitPath

//TODO init with hilt
class ExploreViewModel(private val repo: AppRepository = AppRepository()) : CompositeViewModel() {

    private var _state = MutableLiveData<List<ExploreItem>>()
    val state: LiveData<List<ExploreItem>> = _state

    fun fetchData() {
        findItems("/")
    }

    private fun findItems(findPath: String) {
        val layer = findPath.splitPath()
        repo.getAllFolder { list ->
            val map = list?.filter { item ->
                item.path.startsWith(findPath)
//                item.path.splitPath() == layer
            }?.map {
                ExploreItem(it.key, "${it.key}/")
            } ?: emptyList()
        }
        _state.value =
    }

    fun addFolder(name: String?) {
        if (!name.isNullOrBlank())
            repo.saveFolder(name, "$name/")
    }

}

