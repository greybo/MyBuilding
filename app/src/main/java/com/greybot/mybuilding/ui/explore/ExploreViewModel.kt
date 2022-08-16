package com.greybot.mybuilding.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mybuilding.base.CompositeViewModel
import com.greybot.mybuilding.repository.AppRepository

//TODO init with hilt
class ExploreViewModel(val repo: AppRepository = AppRepository()) : CompositeViewModel() {

    private var _state = MutableLiveData<List<String>>()
    val state: LiveData<List<String>> = _state

    fun fetchData() {
        makeItems()
    }

    private fun makeItems() {
        repo.getAllFolder().groupBy {

        }
        val list = repo.getAllFolder().groupBy {
            it.split("/")[0]
        }
        _state.value = list.keys.toList() //listOf("my building order")
    }
}