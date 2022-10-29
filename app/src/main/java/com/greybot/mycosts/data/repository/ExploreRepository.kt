package com.greybot.mycosts.data.repository

//import android.util.Log
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import com.greybot.mycosts.data.dto.Explore
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.Folder
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.toastDebug
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject
import javax.inject.Singleton

//import com.greybot.mycosts.utility.toastDebug
//import kotlinx.coroutines.CompletableDeferred

@Singleton
class ExploreRepository @Inject constructor() {

    private val uid: String = "654321"
    private val path: String = "exploreNew"
    private val myRef = Firebase.database.reference.child(uid).child(path)
    private val backupList = mutableListOf<Folder>()

    suspend fun getAll(): List<Folder>? {
        val deferred = CompletableDeferred<List<Folder>?>()
        if (backupList.isNotEmpty()) {
            deferred.complete(backupList)
        }
        getRemoteAll({
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

//    suspend fun getFoldersAll(): List<Folder>? {
//        val deferred = CompletableDeferred<List<Folder>?>()
//        if (backupList.isNotEmpty()) {
//            deferred.complete(backupList)
//        }
//        getRemoteAll({
//            if (!equalsList(it, backupList)) {
//                deferred.complete(it)
//            }
//            backupList.clear()
//            backupList.addAll(it)
//        }, { error ->
//            deferred.completeExceptionally(error)
//        })
//        return deferred.await()
//    }

    private fun equalsList(newList: List<Folder>, backupList: MutableList<Folder>): Boolean {
        if (newList.size != backupList.size) return false
        newList.forEachIndexed { index, dto ->
            if (dto != backupList.getOrNull(index)) return false
        }
        return true
    }

    private fun getRemoteAll(success: (List<Folder>) -> Unit, failed: (Throwable) -> Unit) {
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        it.getValue(Folder::class.java)
                    }
                    success.invoke(itemFolder)
                }

                override fun onCancelled(error: DatabaseError) {
                    failed.invoke(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                    toastDebug = error.message
                }
            }
        )
    }

    fun addFolder(item: Folder) {
        val key = myRef.push().key ?: return
        item.objectId = key
        backupList.add(item)
        myRef.child(key).setValue(item) { error, ref ->
            if (error != null) {
                LogApp.e("ExploreFragment", error.toException())
            }
            LogApp.i("ExploreFragment", ref.toString())
        }
    }

    fun update(item: Folder) {
        val database: DatabaseReference = Firebase.database.reference

        if (item.objectId == null) {
            LogApp.w("TAG", null, "Couldn't get push key for posts")
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
}