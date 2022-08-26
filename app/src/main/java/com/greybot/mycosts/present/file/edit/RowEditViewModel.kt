package com.greybot.mycosts.present.file.edit

import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.row.RowDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.models.MeasureType

class RowEditViewModel : CompositeViewModel() {

    private val dataSource = RowDataSource()
    val status = MutableLiveData<RowDto?>()
    fun fetchData(objectId: String?) {
        objectId ?: throw Throwable()
        launchOnDefault {
            val model = dataSource.findById(objectId)
            makeItems(model)
        }
    }
    private fun makeItems(model: RowDto?) {
        status.postValue(model)
    }
}


fun mapToRowItem(item: RowDto) = AdapterItems.RowItem(
    name = item.title,
    path = item.path,
    measure = MeasureType.toType(item.measure),
    price = item.price,
    count = item.count,
    isBought = item.isBought,
    objectId = item.objectId!!,
)
