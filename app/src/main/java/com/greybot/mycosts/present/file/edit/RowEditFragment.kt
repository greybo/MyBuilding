package com.greybot.mycosts.present.file.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
        }
    }
}
