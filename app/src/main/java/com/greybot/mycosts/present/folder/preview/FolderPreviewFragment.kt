package com.greybot.mycosts.present.folder.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.base.systemBackPressedCallback
import com.greybot.mycosts.databinding.FolderPreviewFragmentBinding
import com.greybot.mycosts.dialog.showDialogCosts
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.IRowCost
import com.greybot.mycosts.theme.MyCostsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderPreviewFragment :
    BaseBindingFragment<FolderPreviewFragmentBinding>(FolderPreviewFragmentBinding::inflate) {

    private val log_tag = "FolderPreviewFragment"

    //    private var adapter: ExploreAdapter? = null
    private val router: FolderPreviewRouter by lazy { FolderPreviewRouter(findNavController()) }
    private val viewModel by viewModels<FolderPreviewViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            MyCostsTheme {
                FolderPreviewScreen(viewModel, ::handleAdapterClick)
            }
        }


//        initViews()
//        val toolbar = binding.folderPreviewToolbar.getBuilder()
//            .homeCallback { backPress() }
//            .rightIconCallback {
//                handleOnClickMenu(it)
//            }.create()

//        viewModel.state.observe(viewLifecycleOwner) {
//            adapter?.updateAdapter(it)
//        }
//        viewModel.title.observe(viewLifecycleOwner) {
//            toolbar.title = it ?: ""
//        }
//        viewModel.actionIconLiveData().observe(viewLifecycleOwner) { isShow ->
//            val deleteIcon = if (isShow) {
//                R.drawable.ic_action_delete_outline
//            } else 0
//            toolbar.rightIcon(deleteIcon)
//            adapter?.highlightGlobal = isShow
//        }
        systemBackPressedCallback { viewModel.handleOnClickOptionMenu(ActionButtonType.Back) /*backPress()*/ }
        viewModel.fetchData()
    }

//    private fun handleOnClickMenu(type: ActionButtonType) {
//        viewModel.handleOnClickOptionMenu(type)
//    }

//    private fun backPress() {
//        findNavController().popBackStack()
//    }

//    private fun initViews() {
//        initAdapter()
//    }

//    private fun initAdapter() {
//        with(binding) {
//            adapter = ExploreAdapter {
//                handleAdapterClick(it)
//            }
//            folderPreviewRecyclerView.setHasFixedSize(true)
//            folderPreviewRecyclerView.adapter = adapter
//        }
//    }

    private fun handleAdapterClick(callback: AdapterCallback) {
        with(callback) {
            when (this) {
                is AdapterCallback.RowName -> router.fromFolderToEditRow(this.value.objectId)
                is AdapterCallback.RowPrice -> bottomDialog(this)
                is AdapterCallback.RowCount -> bottomDialog(this)
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

    private fun bottomDialog(
        rowType: AdapterCallback,
//        value: AdapterItems.RowItem
    ) {
        val value = (rowType as? IRowCost)?.value ?: throw Throwable()
        showDialogCosts(rowType, value, viewModel::saveData)
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


}