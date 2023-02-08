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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FileRepo @Inject constructor() {

    private val actor = FileActorCoroutine()
    private val uid: String = "654321"
    private val path: String = "file"
    private val database = Firebase.database.reference
    private val myRef = database.child(uid).child(path)

    suspend fun getAll(force: Boolean = false): List<FileRow> {
        val deferred = CompletableDeferred<List<FileRow>>()

        val list = if (force) {
            getAllData()
        } else {
            actor.getAll() ?: getAllData()
        }
        deferred.complete(list ?: emptyList())
        return deferred.await()
    }

    private suspend fun getAllData(): List<FileRow>? {
        return suspendCoroutine { continuation ->
            myRef.orderByKey().addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val itemFolder = snapshot.children.mapNotNull {
                            it.getValue(FileRow::class.java)
                        }
                        actor.addAll(itemFolder)
                        continuation.resume(itemFolder)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(null)
                        LogApp.e("RowRepo", error.toException())
                    }
                }
            )
        }
    }

    suspend fun addFile(dto: FileRow) {
        val key = myRef.push().key ?: return
        dto.objectId = key
        actor.add(dto)

        myRef.child(key).setValue(dto) { error, ref ->
            if (error != null) {
                LogApp.e("RowRepo", error.toException())
            }
            LogApp.i("RowRepo", ref.toString())
        }
    }


    suspend fun update(item: FileRow): Boolean {
        val database: DatabaseReference = Firebase.database.reference

        if (item.objectId == null) {
            LogApp.w("RowRepo", null, "Couldn't get push key for posts")
            return false
        }
        actor.update(item)

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
        return true
    }

//    private fun equalsList(newList: List<FileRow>, backupList: List<FileRow>): Boolean {
//        if (newList.size != backupList.size) return false
//        newList.forEachIndexed { index, dto ->
//            if (dto != backupList.getOrNull(index)) return false
//        }
//        return true
//    }

//    fun updateBackupList(list: List<FileRow>) {
//        backupList.clear()
//        backupList.addAll(list)
//    }

//    suspend fun getById(objectId: String): FileRow? {
//        val deferred = CompletableDeferred<FileRow?>()
//        myRef.child(objectId).addListenerForSingleValueEvent(
//            object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val itemRow = snapshot.getValue(FileRow::class.java)
//                    deferred.complete(itemRow)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    deferred.completeExceptionally(error.toException())
//                    LogApp.e("RowRepo", error.toException())
//                }
//            }
//        )
//        return deferred.await()
//    }
//
//    suspend fun getByParentId(objectId: String): FileRow? {
//        val deferred = CompletableDeferred<FileRow?>()
//        myRef.child(objectId).addListenerForSingleValueEvent(
//            object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val itemRow = snapshot.getValue(FileRow::class.java)
//                    deferred.complete(itemRow)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    deferred.completeExceptionally(error.toException())
//                    LogApp.e("RowRepo", error.toException())
//                }
//            }
//        )
//        return deferred.await()
//    }

}