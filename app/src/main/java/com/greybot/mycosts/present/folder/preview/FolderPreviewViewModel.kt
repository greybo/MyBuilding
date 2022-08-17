package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.models.ExploreItem
import kotlinx.coroutines.launch

class FolderPreviewViewModel(private val repo: AppRepository = AppRepository()) :
    CompositeViewModel() {

    private var _state = MutableLiveData<List<ExploreItem>>()
    val state: LiveData<List<ExploreItem>> = _state

    fun fetchData(path: String?) {
        path ?: return
        val layer: Int = path.split("/").filter { it.isNotBlank() }.size
        viewModelScope.launch {
            val list = repo.findFolder(path)?.filter { item ->
                item.path.split("/").filter { it.isNotBlank() }.size == layer
            }?.map {
                ExploreItem(it.name, path)
            }
            _state.postValue(list ?: emptyList())
        }
    }

}