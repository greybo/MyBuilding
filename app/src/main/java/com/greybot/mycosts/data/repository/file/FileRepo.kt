package com.greybot.mycosts.data.repository.file

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.utility.LogApp
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FileRepo @Inject constructor() {

    private val actor = FileActor()
    private val uid: String = "654321"
    private val path: String = "file"
    private val database = Firebase.database.reference
    private val userPathRef = database.child(uid).child(path)

    suspend fun getAll(force: Boolean = false): List<FileRow> {
        val list = if (force) {
            getAllData()
        } else {
            actor.getAll() ?: getAllData()
        }
        return list ?: emptyList()
    }

    private suspend fun getAllData(): List<FileRow>? {
        return suspendCoroutine { continuation ->
            userPathRef.orderByKey().addListenerForSingleValueEvent(
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

    suspend fun addFile(model: FileRow) {
        val key = userPathRef.push().key ?: return
        model.objectId = key
        actor.add(model)

        userPathRef.child(key).setValue(model) { error, ref ->
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

    suspend fun deleteFile(objectId: String) {
        actor.remote(objectId, userPathRef)
    }
}