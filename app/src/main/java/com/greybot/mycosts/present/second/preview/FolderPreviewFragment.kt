package com.greybot.mycosts.present.second.preview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.base.getEndSegment
import com.greybot.mycosts.base.systemBackPressedCallback
import com.greybot.mycosts.databinding.FolderPreviewFragmentBinding
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.ExploreAdapter
import com.greybot.mycosts.utility.getRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderPreviewFragment :
    BaseBindingFragment<FolderPreviewFragmentBinding>(FolderPreviewFragmentBinding::inflate) {

    private val viewModel by viewModels<FolderPreviewViewModel>()
    private var adapter: ExploreAdapter? = null
    private val args by lazy { arguments?.let { FolderPreviewFragmentArgs.fromBundle(it) } }
    private val router: IFolderPreviewRouter by getRouter()
    private var buttonType: ButtonType = ButtonType.None

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.folderPreviewToolbar.getBuilder()
            .homeCallback { backPress() }
            .title(getEndSegment(args?.pathName))
            .create()
        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        systemBackPressedCallback { backPress() }

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.fetchData(args?.objectId, args?.pathName)
        }, 300)
    }

    private fun backPress() {
        findNavController().popBackStack()
    }

    private fun initViews() {
        binding.folderPreviewFloatButton.setOnClickListener {
            handleButtonClick(buttonType)
        }
        initAdapter()
    }

    private fun initAdapter() {
        with(binding) {
            adapter = ExploreAdapter {
                handleAdapterClick(it)
            }
            folderPreviewRecyclerView.setHasFixedSize(true)
            folderPreviewRecyclerView.adapter = adapter
        }
    }

    private fun handleAdapterClick(callback: AdapterCallback) {
        with(callback) {
            when (this) {
                is AdapterCallback.Name -> router.fromFolderToEditRow(
                    viewModel.objectId,
                    this.value.path
                )
                is AdapterCallback.Price -> {}
                is AdapterCallback.Buy -> viewModel.changeRowBuy(this.value)
                is AdapterCallback.AddButton -> handleButtonClick(this.value.type)
                is AdapterCallback.FolderOpen -> router.fromFolderToFolder(
                    viewModel.objectId,
                    this.value.path
                )
                else -> {}
            }
        }
    }

    private fun handleButtonClick(
        type: ButtonType,
        path: String = args?.pathName ?: "",
        id: String = viewModel.objectId ?: ""
    ) {
        when (type) {
            ButtonType.Folder -> router.fromFolderToAddFolder(id, path)
            ButtonType.Row -> router.fromFolderToAddRow(id, path)
            else -> {}
        }
    }
}
