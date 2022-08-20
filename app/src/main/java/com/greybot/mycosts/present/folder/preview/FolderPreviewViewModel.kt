package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.greybot.mycosts.base.AddFolderUseCases
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.FindFolderUseCases
import com.greybot.mycosts.base.RowFolderUseCases
import com.greybot.mycosts.models.AdapterItems
import kotlinx.coroutines.async

inline fun <reified T> makeLiveData(any: Any? = null): CustomLiveData<T> {
    return CustomLiveData(any as T)
}

class CustomLiveData<T>(t: T) {
    private val _liveData = MutableLiveData<T>()
    private val liveData: LiveData<T> = _liveData

    init {
        t?.let { _liveData.value = it }
    }

    fun value(t: T) {
        _liveData.value = t as T
    }

    fun postValue(t: T) {
        _liveData.postValue(t as T)
    }

    fun observe2(owner: LifecycleOwner, observer: Observer<T>) {
        liveData.observe(owner, observer)
    }
}

class FolderPreviewViewModel : CompositeViewModel() {

    private val folderAddUseCase get() = AddFolderUseCases()
    private val folderFindUseCase get() = FindFolderUseCases()
    private val rowFindUseCase get() = RowFolderUseCases()

    var stateButton2 = makeLiveData<ButtonType>(ButtonType.None)
//    private var _stateButton = MutableLiveData<FindStateType>()
//    val stateButton: LiveData<FindStateType> = _stateButton

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String?) {
        path ?: return
        launchOnIO {
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
        val type = if (this.isEmpty()) {
            add(AdapterItems.ButtonAddItem(ButtonType.Folder))
            add(AdapterItems.ButtonAddItem(ButtonType.Row))
            ButtonType.None
        } else
            if (this[0] is AdapterItems.FolderItem) {
                ButtonType.Folder
            } else
                ButtonType.Row

        stateButton2.postValue(type)
        return this
    }

//    fun addFolder(name: String?, path: String?) {
//        folderAddUseCase.invoke(name, path)
//    }

}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}