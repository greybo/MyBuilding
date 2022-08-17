package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.models.ExploreItem

class FolderPreviewViewModel(private val repo: AppRepository = AppRepository()) :
    CompositeViewModel() {

    private var _state = MutableLiveData<List<ExploreItem>>()
    val state: LiveData<List<ExploreItem>> = _state

    fun fetchData(path: String?) {
        val layer: Int = path?.split("/")?.filter { it.isNotBlank() }?.size ?: 0
        _state.value = repo.findFolder(path).map {
           val name = it.split("/")[layer]
            ExploreItem(name, it)
        }
    }

}