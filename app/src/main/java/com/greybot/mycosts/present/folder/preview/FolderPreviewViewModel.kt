package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.AddFolderUseCases
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.FindFolderUseCases
import com.greybot.mycosts.base.RowFolderUseCases
import com.greybot.mycosts.models.AdapterItems
import kotlinx.coroutines.async

inline fun <reified T> makeLiveData(): CustomLiveData<T> {
    return CustomLiveData<T>()
}

class CustomLiveData<T> {
    private val _liveData = MutableLiveData<T?>()
    private val liveData: LiveData<T?> = _liveData

    fun value(t: T) {
        _liveData.value = t
    }

    fun observe2(owner: LifecycleOwner, observer: Observer<T?>) {
        liveData.observe(owner, observer)
    }
}

class FolderPreviewViewModel : CompositeViewModel() {

    private val folderAddUseCase get() = AddFolderUseCases()
    private val folderFindUseCase get() = FindFolderUseCases()
    private val rowFindUseCase get() = RowFolderUseCases()
    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String?) {
        path ?: return
        launchOnDefault {
            val folderSet = async { folderFindUseCase.invoke(path) }
            val rowSet = async { rowFindUseCase.invoke(path) }

            handleResult(folderSet.await()?.toMutableList(), rowSet.await()?.toMutableList())
        }
    }

    private fun handleResult(
        folderList: MutableList<AdapterItems>?,
        rowList: MutableList<AdapterItems>?
    ) {
        val itemList = if (!folderList.isNullOrEmpty()) folderList
        else if (!rowList.isNullOrEmpty()) rowList
        else mutableListOf()

        _state.postValue(itemList.addButton())
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
        folderAddUseCase.invoke(name, path)
    }

}