package com.greybot.mycosts.present.folder.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.FolderAddFragmentBinding

class FolderAddFragment :
    BaseBindingFragment<FolderAddFragmentBinding>(FolderAddFragmentBinding::inflate) {

    private val viewModel by viewModels<FolderAddViewModel>()
    private val args: FolderAddFragmentArgs? by lazy {
        arguments?.let {
            FolderAddFragmentArgs.fromBundle(
                it
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            addFolderSaveButton.setOnClickListener {
//                setFragmentResult(
//                    FRAGMENT_RESULT_ADD_FOLDER,
//                    bundleOf(
//                        ARG_FOLDER_NAME to addFolderName.text.toString(),
//                        ARG_FOLDER_PATH to args?.path
//                    )
//                )
                viewModel.addFolder(addFolderName.text.toString(), args?.path)
                findNavController().popBackStack()
            }
        }
    }


}