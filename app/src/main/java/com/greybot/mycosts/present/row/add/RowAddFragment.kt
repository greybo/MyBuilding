package com.greybot.mycosts.present.row.add

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.RowAddFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RowAddFragment : BaseBindingFragment<RowAddFragmentBinding>(RowAddFragmentBinding::inflate),
    TextView.OnEditorActionListener {

    private val args by lazy { arguments?.let { RowAddFragmentArgs.fromBundle(it) } }
    private val viewModel by viewModels<RowAddViewModel>()
    private val objectId: String get() = args?.objectId ?: throw Throwable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData(objectId)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            addRowName.setOnEditorActionListener(this@RowAddFragment)
            addRowCount.setOnEditorActionListener(this@RowAddFragment)
            addRowPrice.setOnEditorActionListener(this@RowAddFragment)
            addRowButton.setOnClickListener {
                saveFile()
            }
        }
    }

    private fun saveFile() {
        with(binding) {
            val _price = addRowPrice.text.toString().ifBlank {
                0F
            }.toString().toFloat()
            viewModel.addRow(
                path = "",//args!!.path,
                rowName = addRowName.text.toString(),
                count = addRowCount.text.toString(),
                price = _price,
                parentId = args?.objectId
            )

        }
        findNavController().popBackStack()
    }

    override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // Do whatever you want here
            saveFile()
            return true;
        }
        return false;
    }
}