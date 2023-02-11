package com.greybot.mycosts.present.file.edit

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.databinding.RowAddFragmentBinding
import com.greybot.mycosts.utility.round2String
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RowEditFragment :
    BaseBindingFragment<RowAddFragmentBinding>(RowAddFragmentBinding::inflate),
    TextView.OnEditorActionListener {

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
            addRowName.setText(rowDto?.name)
            addRowCount.setText(rowDto?.count?.round2String())
            addRowPrice.setText(rowDto?.price?.round2String())

            addRowName.setOnEditorActionListener(this@RowEditFragment)
            addRowCount.setOnEditorActionListener(this@RowEditFragment)
            addRowPrice.setOnEditorActionListener(this@RowEditFragment)
            addRowButton.setOnClickListener {
                saveFile()
            }
        }
    }

    override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // Do whatever you want here
            saveFile()
            return true;
        }
        return false;
    }

    private fun saveFile() {
        with(binding) {
            viewModel.update(
                rowName = addRowName.text.toString(),
                count = addRowCount.text.toString(),
                price = addRowPrice.text.toString(),
            )

        }
        findNavController().popBackStack()
    }
}
