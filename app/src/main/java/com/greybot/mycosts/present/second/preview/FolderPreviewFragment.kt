package com.greybot.mycosts.present.second.preview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.base.systemBackPressedCallback
import com.greybot.mycosts.databinding.FolderPreviewFragmentBinding
import com.greybot.mycosts.databinding.SampleDialogOneBinding
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.ExploreAdapter
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.bindingDialog
import com.greybot.mycosts.utility.getRouter
import com.greybot.mycosts.utility.showKeyboard
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
            .create()
        viewModel.state.observe(viewLifecycleOwner) {
            adapter?.updateAdapter(it)
        }
        viewModel.title.observe(viewLifecycleOwner) {
            toolbar.title = it ?: ""
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
                is AdapterCallback.RowPrice -> showDialogOne(this, this.value)
                is AdapterCallback.RowCount -> showDialogOne(this, this.value)
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

    private fun showDialogOne(action: AdapterCallback, model: AdapterItems.RowItem) {
        val dialog = BottomSheetDialog(requireContext())
        val binding = bindingDialog(requireContext(), SampleDialogOneBinding::inflate)

        dialog.setContentView(binding.root)

        binding.bottomSheetEditCount.setText(model.count.toString())
        binding.bottomSheetEditPrice.setText(model.price.toString())

        binding.bottomSheetEditPrice.setOnEditorActionListener { _, editorInfo, _ ->
            if (editorInfo == EditorInfo.IME_ACTION_DONE) {
                saveData(binding, model)
                dialog.dismiss()
                true
            } else
                false
        }
        dialog.setOnShowListener {
            when (action) {
                is AdapterCallback.RowPrice -> {
//                    showKeyboardInner(binding.bottomSheetEditPrice)
                    showKeyboard(binding.bottomSheetEditPrice)
                    binding.bottomSheetEditPrice.selectAll()
                }
                is AdapterCallback.RowCount -> {
//                    showKeyboardInner(binding.bottomSheetEditCount)
                    showKeyboard(binding.bottomSheetEditCount)
                    binding.bottomSheetEditCount.selectAll()
                }
                else -> {}
            }
        }
        dialog.show()
    }

    private fun saveData(
        binding: SampleDialogOneBinding,
        model: AdapterItems.RowItem
    ) {
        binding.bottomSheetEditCount.text?.toString()
        val count = if (binding.bottomSheetEditCount.text.isNotEmpty()) {
            binding.bottomSheetEditCount.text.toString().toInt()
        } else
            model.count

        val price = if (binding.bottomSheetEditPrice.text.isNotEmpty()) {
            binding.bottomSheetEditPrice.text.toString().toFloat()
        } else {
            model.price
        }
        LogApp.i(log_tag, "$count | $price")

        viewModel.changeRowPrice(id = model.objectId, count = count, price = price)
    }

    private fun showKeyboardInner(text: EditText) {
        Handler(Looper.getMainLooper()).postDelayed({
            showKeyboard(text)
        }, 200)
    }
}