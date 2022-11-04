package com.greybot.mycosts.data.repository.row

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.utility.LogApp
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepo @Inject constructor() {
    private val uid: String = "123456"
    private val path: String = "rows"
    private val database = Firebase.database.reference
    private val myRef = database.child(uid).child(path)
    val backupList = mutableListOf<FileRow>()

    suspend fun getAll(): List<FileRow> {
        val deferred = CompletableDeferred<List<FileRow>>()
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

    private fun equalsList(newList: List<FileRow>, backupList: List<FileRow>): Boolean {
        if (newList.size != backupList.size) return false
        newList.forEachIndexed { index, dto ->
            if (dto != backupList.getOrNull(index)) return false
        }
        return true
    }

    private fun getAllData(success: (List<FileRow>) -> Unit, failed: (Throwable) -> Unit) {
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        it.getValue(FileRow::class.java)
                    }
                    success(itemFolder)
                }

                override fun onCancelled(error: DatabaseError) {
                    failed(error.toException())
                    LogApp.e("RowRepo", error.toException())
                }
            }
        )
    }

    suspend fun getById(objectId: String): FileRow? {
        val deferred = CompletableDeferred<FileRow?>()
        myRef.child(objectId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemRow = snapshot.getValue(FileRow::class.java)
                    deferred.complete(itemRow)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                    LogApp.e("RowRepo", error.toException())
                }
            }
        )
        return deferred.await()
    }

    suspend fun getByParentId(objectId: String): FileRow? {
        val deferred = CompletableDeferred<FileRow?>()
        myRef.child(objectId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemRow = snapshot.getValue(FileRow::class.java)
                    deferred.complete(itemRow)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                    LogApp.e("RowRepo", error.toException())
                }
            }
        )
        return deferred.await()
    }

    fun addFile(dto: FileRow) {
        val key = myRef.push().key ?: return
        dto.parentObjectId = key
        backupList.add(dto)

        myRef.child(key).setValue(dto) { error, ref ->
            if (error != null) {
                LogApp.e("RowRepo", error.toException())
            }
            LogApp.i("RowRepo", ref.toString())
        }
    }


    fun saveModel(item: FileRow) {
        val database: DatabaseReference = Firebase.database.reference

        if (item.objectId == null) {
            LogApp.w("RowRepo", null, "Couldn't get push key for posts")
            return
        }

        val dtoMap = item.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/$uid/$path/${item.objectId}" to dtoMap
        )

        database.updateChildren(childUpdates)
//            .addOnSuccessListener {
//                LogApp.i("writeNewPost", "success")
//            }
        /*.addOnFailureListener {
                LogApp.e("writeNewPost", it)
            }*/
    }

    fun saveBackupList(list: List<FileRow>) {
        backupList.clear()
        backupList.addAll(list)
    }
}