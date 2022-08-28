package com.greybot.mycosts.present.file.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.RowAddFragmentBinding

class RowAddFragment : BaseBindingFragment<RowAddFragmentBinding>(RowAddFragmentBinding::inflate) {

    private val args by lazy { arguments?.let { RowAddFragmentArgs.fromBundle(it) } }
    private val viewModel by viewModels<RowAddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        args?.path ?: throw Throwable()
        with(binding) {
            addRowButton.setOnClickListener {
                viewModel.addRow(
                    path = args!!.path,
                    rowName = addRowName.text.toString(),
                    count = addRowCount.text.toString(),
                    price = addRowPrice.text.toString().toFloat(),
                    parentId= args?.objectId
                )
                findNavController().popBackStack()
            }
        }
    }
}