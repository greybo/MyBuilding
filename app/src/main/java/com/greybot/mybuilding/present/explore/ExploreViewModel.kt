package com.greybot.mybuilding.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mybuilding.base.CompositeViewModel
import com.greybot.mybuilding.data.repository.AppRepository

//TODO init with hilt
class ExploreViewModel(private val repo: AppRepository = AppRepository()) : CompositeViewModel() {

    private var _state = MutableLiveData<List<String>>()
    val state: LiveData<List<String>> = _state

    fun fetchData() {
        makeItems()
    }

    private fun makeItems() {
        val list = repo.getAllFolder().groupBy {
            it.split("/")[0]
        }
        _state.value = list.keys.toList() //listOf("my building order")
    }
}