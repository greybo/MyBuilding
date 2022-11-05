package com.greybot.mycosts.present.file.edit

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.databinding.RowEditFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RowEditFragment :
    BaseBindingFragment<RowEditFragmentBinding>(RowEditFragmentBinding::inflate) {

    private val viewModel by viewModels<RowEditViewModel>()
    private val args by lazy { arguments?.let { RowEditFragmentArgs.fromBundle(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.status.observe(viewLifecycleOwner) {
            initView(it)
        }
        viewModel.fetchData(args?.objectId)
    }

    private fun initView(rowDto: FileRow?) {
        with(binding) {
            editRowName.setText(rowDto?.name)
            editRowName.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val editModel = rowDto?.copy(name = editRowName.text.toString())
                    viewModel.editRow(editModel)
                    findNavController().popBackStack()
                    true
                } else false
            }
        }
    }
}
