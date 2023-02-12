package com.greybot.mycosts.present.folder.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.base.systemBackPressedCallback
import com.greybot.mycosts.databinding.FolderPreviewFragmentBinding
import com.greybot.mycosts.dialog.showDialogCosts
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.ExploreAdapter
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.getRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderPreviewFragment :
    BaseBindingFragment<FolderPreviewFragmentBinding>(FolderPreviewFragmentBinding::inflate) {

    private val log_tag = "FolderPreviewFragment"
    private val viewModel by viewModels<FolderPreviewViewModel>()
    private var adapter: ExploreAdapter? = null
    private val router: IFolderPreviewRouter by getRouter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val toolbar = binding.folderPreviewToolbar.getBuilder()
            .homeCallback { backPress() }
            .rightCallback {
                viewModel.deleteSelectItems()
            }
            .create()
        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        viewModel.title.observe(viewLifecycleOwner) {
            toolbar.title = it ?: ""
        }
        viewModel.deleteIconLiveData().observe(viewLifecycleOwner) {
            toolbar.rightButtonShow(it, "Delete")
            adapter?.highlightGlobal = it
        }
        systemBackPressedCallback { backPress() }
        viewModel.fetchData()
    }

    private fun backPress() {
        findNavController().popBackStack()
    }

    private fun initViews() {
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
                is AdapterCallback.RowName -> router.fromFolderToEditRow(this.value.objectId)
                is AdapterCallback.RowPrice -> showDialogCosts(this, this.value) {
                    saveData(it)
                }
                is AdapterCallback.RowCount -> showDialogCosts(this, this.value) {
                    saveData(it)
                }
                is AdapterCallback.FileHighlight -> viewModel.fileHighlight(this.value.objectId)
                is AdapterCallback.RowBuy -> viewModel.changeRowBuy(this.value)
                is AdapterCallback.AddButton -> handleButtonClick(this.value.type)
                is AdapterCallback.FolderOpen -> {
                    router.fromFolderToFolder(
                        this.value.objectId ?: throw Throwable("objectId must not be empty")
                    )
                }
                else -> {}
            }
        }
    }

    private fun handleButtonClick(
        type: ButtonType,
        id: String = viewModel.parentId
    ) {
        when (type) {
            ButtonType.Folder -> router.fromFolderToAddFolder(id)
            ButtonType.Row -> router.fromFolderToAddRow(id)
            else -> {}
        }
    }

    private fun saveData(model: AdapterItems.RowItem) {
        LogApp.d(log_tag, "${model.count} | ${model.price}")
        viewModel.changeRowPrice(id = model.objectId, count = model.count, price = model.price)
    }
}