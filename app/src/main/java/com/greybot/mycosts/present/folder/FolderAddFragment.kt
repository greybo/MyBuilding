package com.greybot.mycosts.present.folder

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.FolderAddFragmentBinding
import com.greybot.mycosts.utility.ARG_FOLDER_NAME
import com.greybot.mycosts.utility.ARG_FOLDER_PATH
import com.greybot.mycosts.utility.FRAGMENT_RESULT_ADD_FOLDER

class FolderAddFragment :
    BaseBindingFragment<FolderAddFragmentBinding>(FolderAddFragmentBinding::inflate) {

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
                setFragmentResult(
                    FRAGMENT_RESULT_ADD_FOLDER,
                    bundleOf(
                        ARG_FOLDER_NAME to addFolderName.text.toString(),
                        ARG_FOLDER_PATH to args?.path
                    )
                )
                findNavController().popBackStack()
            }
        }
    }


}