package com.greybot.mycosts.present.second.add

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.FolderAddFragmentBinding
import com.greybot.mycosts.utility.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FolderAddFragment :
    BaseBindingFragment<FolderAddFragmentBinding>(FolderAddFragmentBinding::inflate) {

    private val viewModel by viewModels<FolderAddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
//        viewModel.fetchData(viewModel.objectId)
    }

    private fun initViews() {
        with(binding) {
            val timestamp = Date().time
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            addFolderDate.text = sdf.format(timestamp)
            addFolderName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        saveFolder(addFolderName.text.toString(), timestamp)
                        return true
                    }
                    return false
                }
            })
            addFolderName.requestFocus()
            showKeyboard()
        }
    }

    private fun saveFolder(name: String, timestamp: Long) {
        viewModel.addFolderNew(name, timestamp)
        findNavController().popBackStack()
    }
}