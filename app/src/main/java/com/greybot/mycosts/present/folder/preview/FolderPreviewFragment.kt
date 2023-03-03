package com.greybot.mycosts.present.folder.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.systemBackPressedCallback
import com.greybot.mycosts.components.toolbar.ActionButtonType
import com.greybot.mycosts.dialog.showDialogCosts
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.IRowCost
import com.greybot.mycosts.theme.MyCostsTheme
import com.greybot.mycosts.utility.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderPreviewFragment : Fragment() {
//    BaseBindingFragment<FolderPreviewFragmentBinding>(FolderPreviewFragmentBinding::inflate)


    private val log_tag = "FolderPreviewFragment"

    private val router: FolderPreviewRouter by lazy { FolderPreviewRouter(findNavController()) }
    private val viewModel by viewModels<FolderPreviewViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        hideKeyboard()
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyCostsTheme {
                    FolderPreviewScreen(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.dialogCostsLiveData.observe(viewLifecycleOwner){
////            bottomDialog(it.value)
//        }
        systemBackPressedCallback { viewModel.handleOnClickOptionMenu(ActionButtonType.Back) /*backPress()*/ }
        viewModel.router = router
        viewModel.fetchData()
    }

    private fun bottomDialog(
        rowType: AdapterCallback?
    ) {
        val value = (rowType as? IRowCost)?.value ?: throw Throwable()
        showDialogCosts(rowType, value, viewModel::saveData)
    }
}