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
import com.greybot.mycosts.utility.Event
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

    var value: T = t
        set(v) {
            value(v)
        }

    private fun value(t: T) {
        _liveData.value = t as T
    }

    fun postValue(t: T) {
        _liveData.postValue(t as T)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        liveData.observe(owner, observer)
    }
}

class FolderPreviewViewModel : CompositeViewModel() {

    private val folderAddUseCase get() = AddFolderUseCases()
    private val folderFindUseCase get() = FindFolderUseCases()
    private val rowFindUseCase get() = RowFolderUseCases()

    private val _stateButton = MutableLiveData<Event<ButtonType>>()
    val stateButton: LiveData<Event<ButtonType>> = _stateButton

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

        when (itemList.buttonState()){
            ButtonType.None ->{
                itemList.add(AdapterItems.ButtonAddItem(ButtonType.Folder))
                itemList.add(AdapterItems.ButtonAddItem(ButtonType.Row))
            }
            ButtonType.Row->{
                val total = itemList.fold(0F) { t, item  ->
                    t + (item as AdapterItems.RowItem).price
                }
                itemList.add(AdapterItems.TotalItem(total))
            }
            else -> {}
        }

        _state.postValue(itemList)
    }

    private fun MutableList<AdapterItems>.buttonState(): ButtonType {
        val type = when (this.getOrNull(0)) {
            is AdapterItems.FolderItem -> ButtonType.Folder
            is AdapterItems.RowItem -> ButtonType.Row
            else -> ButtonType.None
        }

        _stateButton.postValue(Event(type))
        return type
    }

}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}