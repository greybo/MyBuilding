package com.greybot.mycosts.present.folder.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.FolderPreviewFragmentBinding
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.explore.ExploreAdapter
import com.greybot.mycosts.utility.ARG_FOLDER_NAME
import com.greybot.mycosts.utility.ARG_FOLDER_PATH
import com.greybot.mycosts.utility.FRAGMENT_RESULT_ADD_FOLDER
import com.greybot.mycosts.utility.getRouter

class FolderPreviewFragment :
    BaseBindingFragment<FolderPreviewFragmentBinding>(FolderPreviewFragmentBinding::inflate) {

    private val viewModel by viewModels<FolderPreviewViewModel>()
    private var adapter: ExploreAdapter? = null
    private var args: FolderPreviewFragmentArgs? = null
    private val router: IFolderPreviewRouter by getRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            args = FolderPreviewFragmentArgs.fromBundle(it)
        }
        setFragmentResultListener(FRAGMENT_RESULT_ADD_FOLDER) { key, bundle ->
            if (FRAGMENT_RESULT_ADD_FOLDER == key) {
                val name = (bundle.get(ARG_FOLDER_NAME) as? String)
                val path = (bundle.get(ARG_FOLDER_PATH) as? String)
                viewModel.addFolder(name, path)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        viewModel.fetchData(args?.pathName)
    }

    private fun initAdapter() {
        adapter = ExploreAdapter {
            handleClick(it)
        }
        binding.folderPreviewRecyclerView.setHasFixedSize(true)
        binding.folderPreviewRecyclerView.adapter = adapter
    }

    private fun handleClick(item: AdapterItems) {
        when (item) {
            is AdapterItems.ButtonAddItem -> {
                if (item.name == "Folder") router.fromFolderToAddFolder(args?.pathName ?: "")
                else router.fromFolderToAddRow(args?.pathName ?: "")
            }
            is AdapterItems.FolderItem -> router.fromFolderToFolder(item.path)
            is AdapterItems.RowItem -> router.fromFolderToFolder(item.path)
        }
    }
}