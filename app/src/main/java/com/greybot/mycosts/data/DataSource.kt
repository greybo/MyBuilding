package com.greybot.mycosts.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.Post
import com.greybot.mycosts.utility.LogApp


class DataSource {
    private val uid: String = "1234512345"
//        get() = Firebase.auth.currentUser!!.uid

    private val myReExplore: DatabaseReference by lazy { Firebase.database.reference.child("explore/$uid") }

    //    private val database: DatabaseReference by lazy { Firebase.database.reference }
    val exploreList: List<String> = fakeData
    val tag = "DataSource"

    fun getAllData() {
        // Read from the database
        // Read from the database
        myReExplore.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                LogApp.i(tag, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                LogApp.w(tag, error.toException(), "Failed to read value.")
            }
        })
    }

//    fun saveExplore(item: ItemFolderDTO) {
//        val key = myReExplore.push().key ?: return
//
//        myReExplore.setValue(item)
//    }

//    fun saveFolder(name: String, path: String) {
//        val item = ItemFolderDTO(name = name, path = path)
//        myReExplore.setValue(item)
//    }






    private fun writeNewPost(userId: String, username: String, title: String, body: String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val database: DatabaseReference = Firebase.database.reference
        val key = database.child("posts").push().key
        if (key == null) {
            Log.w("TAG", "Couldn't get push key for posts")
            return
        }

        val post = Post(userId, username, title, body)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$userId/$key" to postValues
        )

        database.updateChildren(childUpdates)
            .addOnSuccessListener {
                LogApp.i("writeNewPost", "success")
            }
        /*.addOnFailureListener {
                LogApp.e("writeNewPost", it)
            }*/
    }


//    private fun submitPost() {
//        Toast.makeText(context, "Posting...", Toast.LENGTH_SHORT).show()
//
//        val userId = uid
//        database.child("users").child(userId).addListenerForSingleValueEvent(
//            object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // Get user value
//                    val user = dataSnapshot.getValue<User>()
//
//                    if (user == null) {
//                        // User is null, error out
//                        Log.e(TAG, "User $userId is unexpectedly null")
//                        Toast.makeText(
//                            context,
//                            "Error: could not fetch user.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        // Write new post
//                        writeNewPost(userId, user.username.toString(), title, body)
//                    }
//
//                    setEditingEnabled(true)
//                    findNavController().navigate(R.id.action_NewPostFragment_to_MainFragment)
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.w(TAG, "getUser:onCancelled", databaseError.toException())
//                    setEditingEnabled(true)
//                }
//            })
//    }
}