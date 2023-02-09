package com.greybot.mycosts.present.file.add

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.RowAddFragmentBinding
import com.greybot.mycosts.utility.hideKeyboard
import com.greybot.mycosts.utility.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RowAddFragment : BaseBindingFragment<RowAddFragmentBinding>(RowAddFragmentBinding::inflate),
    TextView.OnEditorActionListener {

    private val viewModel by viewModels<RowAddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            addRowPrice.setOnEditorActionListener(this@RowAddFragment)
            addRowButton.setOnClickListener {
                saveFile()
            }
            addRowName.requestFocus()
            showKeyboard()
        }
    }

    private fun saveFile() {
        with(binding) {
            val name = addRowName.text.toString()
            if (name.isNotBlank()) {
                viewModel.addRow(
                    rowName = name,
                    count = addRowCount.text,
                    price = addRowPrice.text,
                )
            }
        }
        findNavController().popBackStack()
    }

//    private fun Editable?.getPrice(): Float {
//        return this.toString()
//            .ifBlank { 0F }.toString()
//            .toFloat()
//    }

    override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard()
            saveFile()
            return true
        }
        return false
    }
}