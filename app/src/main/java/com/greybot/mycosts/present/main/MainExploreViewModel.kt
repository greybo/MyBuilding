package com.greybot.mycosts.present.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.data.repository.explore.getOrNull
import com.greybot.mycosts.models.AdapterItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainExploreViewModel @Inject constructor(private val dataSource: ExploreDataSource) :
    CompositeViewModel() {

//    private val exploreHandler by lazy { ExploreHandler() }

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    private val observer = Observer<Map<String, List<ExploreRow>>> {
        val folders = it.getOrNull("root")
        val items = makeFolderItems(folders)
        _state.postValue(items)
    }

    init {
        dataSource.listLiveData.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        dataSource.listLiveData.removeObserver(observer)
    }

    fun fetchData() {
        dataSource.fetchData()
    }

    private fun makeFolderItems(explores: List<ExploreRow>?): List<AdapterItems> {
        return explores?.map { f ->
//                val currentPath = Path(path).addToPath(name)
//                val currentFolder = entry.value.find { it.path == currentPath }
            AdapterItems.FolderItem(
                f.name ?: "2222",
                "",
                countInner = "count:${111}",
                total = "total: 0",
                objectId = f.objectId
            )
        } ?: emptyList()
    }
}


