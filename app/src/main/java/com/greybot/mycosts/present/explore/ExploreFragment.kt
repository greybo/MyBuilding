package com.greybot.mycosts.present.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.data.dto.ItemFolderDTO
import com.greybot.mycosts.databinding.ExploreFragmentBinding
import com.greybot.mycosts.utility.ARG_FOLDER_NAME
import com.greybot.mycosts.utility.FRAGMENT_RESULT_ADD_FOLDER
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.getRouter


class ExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private var adapter: ExploreAdapter? = null
    private val router: IExploreRouter by getRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(FRAGMENT_RESULT_ADD_FOLDER) { key, bundle ->
            if (FRAGMENT_RESULT_ADD_FOLDER == key) {
                val name = (bundle.get(ARG_FOLDER_NAME) as? String) ?: ""
                viewModel.addFolder(name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViews()

        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        viewModel.fetchData()
    }


    private fun initAdapter() {
        adapter = ExploreAdapter {
            router.fromExploreToFolder(it)
        }
        binding.mainRecyclerViewX.setHasFixedSize(true)
        binding.mainRecyclerViewX.adapter = adapter
    }

    private fun initViews() {
        binding.exploreFloatButton.setOnClickListener {
            router.fromExploreToAddFolder()
        }
    }

    private fun getFolderAll(callback: (List<ItemFolderDTO>?) -> Unit) {
        val uid: String = "123456"
        val path: String = "explore"
        val database = Firebase.database.reference
        database.child(uid).child(path).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.getValue(ItemFolderDTO::class.java)
                    callback.invoke(listOfNotNull(itemFolder))

                    snapshot.children.map {
                        LogApp.i("getFolderTest", "${it.key} = ${it.value as String}")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.invoke(null)
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
    }

    private fun saveFolder(item: ItemFolderDTO) {
        val uid: String = "123456"
        val path: String = "explore"

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(uid)
        val key = myRef.push().key ?: return

        myRef.child(key).child(path).setValue(item) { error, ref ->
            if (error != null) {
                LogApp.e("ExploreFragment", error.toException())
            }
            LogApp.i("ExploreFragment", ref.toString())
        }
    }
}

