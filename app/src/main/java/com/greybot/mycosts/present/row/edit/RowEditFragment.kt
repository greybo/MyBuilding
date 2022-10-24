package com.greybot.mycosts.present.row.edit

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.databinding.RowEditFragmentBinding

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

    private fun initView(rowDto: RowDto?) {
        with(binding) {
            editRowName.setText(rowDto?.title)
            editRowName.setOnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val editModel = rowDto?.copy(title = editRowName.text.toString())
                    viewModel.editRow(editModel)
                    findNavController().popBackStack()
                    true
                } else false
            }
        }
    }
}
