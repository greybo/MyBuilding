package com.greybot.mycosts.present.second.preview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import au.com.crownresorts.crma.extensions.gone
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.base.getEndSegment
import com.greybot.mycosts.base.systemBackPressedCallback
import com.greybot.mycosts.databinding.FolderPreviewFragmentBinding
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.ExploreAdapter
import com.greybot.mycosts.utility.animateFabHide
import com.greybot.mycosts.utility.animateShowFab
import com.greybot.mycosts.utility.getRouter

class FolderPreviewFragment :
    BaseBindingFragment<FolderPreviewFragmentBinding>(FolderPreviewFragmentBinding::inflate) {

    private val viewModel by viewModels<FolderPreviewViewModel>()
    private var adapter: ExploreAdapter? = null
    private var args: FolderPreviewFragmentArgs? = null
    private val router: IFolderPreviewRouter by getRouter()
    private var buttonType: ButtonType = ButtonType.None

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            args = FolderPreviewFragmentArgs.fromBundle(it)
        }
    }

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
        viewModel.stateButton.observe(viewLifecycleOwner) { event ->
            setStateButton(event.getContentIfNotHandled())
        }
        systemBackPressedCallback { backPress() }
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.fetchData(args?.pathName)
        }, 300)
    }

    private fun backPress() {
        animateFabHide { findNavController().popBackStack() }
    }

    private fun animateFabHide(callback: (() -> Unit)? = null) {
        binding.folderPreviewFloatButton.animateFabHide(callback)
    }

    private fun setStateButton(type: ButtonType?) {
        type ?: return
        when (type) {
            ButtonType.Folder,
            ButtonType.Row -> binding.folderPreviewFloatButton.animateShowFab()
            else -> binding.folderPreviewFloatButton.gone()
        }
        buttonType = type
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
                is AdapterCallback.Name -> animateFabHide { router.fromFolderToEditRow(this.value.objectId) }
                is AdapterCallback.Price -> {}
                is AdapterCallback.Buy -> viewModel.changeRowBuy(this.value)
                is AdapterCallback.Append -> handleButtonClick(this.value.type)
                is AdapterCallback.FolderOpen -> animateFabHide { router.fromFolderToFolder(this.value.path) }
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
            ButtonType.Folder -> animateFabHide { router.fromFolderToAddFolder(path) }
            ButtonType.Row -> animateFabHide { router.fromFolderToAddRow(path, id) }
            else -> {}
        }
    }
}
