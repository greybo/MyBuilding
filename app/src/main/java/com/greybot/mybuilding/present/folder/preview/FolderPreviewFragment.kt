package com.greybot.mybuilding.present.folder.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.greybot.mybuilding.base.BaseBindingFragment
import com.greybot.mybuilding.databinding.FolderPreviewFragmentBinding
import com.greybot.mybuilding.present.explore.ExploreAdapter
import com.greybot.mybuilding.present.explore.getRouter

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
//            arguments?.clear()
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
            router.fromFolderToFolder("$it/")
        }
        binding.folderPreviewRecyclerView.setHasFixedSize(true)
        binding.folderPreviewRecyclerView.adapter = adapter
    }
}