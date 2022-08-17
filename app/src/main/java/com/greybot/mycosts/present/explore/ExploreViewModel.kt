package com.greybot.mycosts.present.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.models.ExploreItem

//TODO init with hilt
class ExploreViewModel(private val repo: AppRepository = AppRepository()) : CompositeViewModel() {

    private var _state = MutableLiveData<List<ExploreItem>>()
    val state: LiveData<List<ExploreItem>> = _state

    fun fetchData() {
        makeItems()
    }

    private fun makeItems() {
        val list = repo.getAllFolder().groupBy {
            it.split("/")[0]
        }

        _state.value = list.entries.map {
            ExploreItem(it.key, "${it.key}/")
        } //listOf("my building order")
    }
}