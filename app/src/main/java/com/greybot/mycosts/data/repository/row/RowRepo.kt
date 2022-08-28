package com.greybot.mycosts.data.repository.row

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.getBoolean
import com.greybot.mycosts.utility.LogApp
import kotlinx.coroutines.CompletableDeferred

//TODO init with HILT
class RowRepo() {
    private val uid: String = "123456"
    private val path: String = "rows"
    private val database = Firebase.database.reference
    private val myRef = database.child(path).child(uid)
    val backupList = mutableListOf<RowDto>()

    suspend fun getAll(): List<RowDto> {
        val deferred = CompletableDeferred<List<RowDto>>()
        if (backupList.isNotEmpty()) {
            deferred.complete(backupList)
        }
        getAllData({
            if (!equalsList(it, backupList)) {
                deferred.complete(it)
            }
            backupList.clear()
            backupList.addAll(it)
        }, { error ->
            deferred.completeExceptionally(error)
        })
        return deferred.await()
    }

    private fun equalsList(newList: List<RowDto>, backupList: List<RowDto>): Boolean {
        if (newList.size != backupList.size) return false
        newList.forEachIndexed { index, dto ->
            if (dto != backupList.getOrNull(index)) return false
        }
        return true
    }

    private fun getAllData(success: (List<RowDto>) -> Unit, failed: (Throwable) -> Unit) {
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        val dto = it.getValue(RowDto::class.java)
                        val isBought = it.getBoolean("isBought")
                        dto?.copy(isBought = isBought)
                    }
                    success(itemFolder)
                }

                override fun onCancelled(error: DatabaseError) {
                    failed(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
    }

    suspend fun getById(objectId: String): RowDto? {
        val deferred = CompletableDeferred<RowDto?>()
        myRef.child(objectId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemRow = snapshot.getValue(RowDto::class.java)
                    deferred.complete(itemRow)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
        return deferred.await()
    }

    suspend fun getByParentId(objectId: String): RowDto? {
        val deferred = CompletableDeferred<RowDto?>()
        myRef.child(objectId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemRow = snapshot.getValue(RowDto::class.java)
                    deferred.complete(itemRow)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
        return deferred.await()
    }

    fun addRow(dto: RowDto) {
        val key = myRef.push().key ?: return
        dto.objectId = key
        backupList.add(dto)

        myRef.child(key).setValue(dto) { error, ref ->
            if (error != null) {
                LogApp.e("ExploreFragment", error.toException())
            }
            LogApp.i("ExploreFragment", ref.toString())
        }
    }

    fun update(objectId: String) {
        val list = backupList.map { row ->
            if (row.objectId == objectId) {
                val changedRow = row.copy(isBought = !row.isBought)
                update(changedRow)
                changedRow
            } else row
        }
        backupList.clear()
        backupList.addAll(list)
    }

    fun update(item: RowDto) {
        val database: DatabaseReference = Firebase.database.reference

        if (item.objectId == null) {
            Log.w("TAG", "Couldn't get push key for posts")
            return
        }

        val dtoMap = item.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/$path/$uid/${item.objectId}" to dtoMap
        )

        database.updateChildren(childUpdates)
//            .addOnSuccessListener {
//                LogApp.i("writeNewPost", "success")
//            }
        /*.addOnFailureListener {
                LogApp.e("writeNewPost", it)
            }*/
    }
}