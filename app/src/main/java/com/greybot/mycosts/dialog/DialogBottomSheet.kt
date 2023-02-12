package com.greybot.mycosts.dialog

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.greybot.mycosts.databinding.SampleDialogOneBinding
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.utility.bindingDialog
import com.greybot.mycosts.utility.round2Double
import com.greybot.mycosts.utility.round2String
import com.greybot.mycosts.utility.showKeyboardWithDelay

fun Fragment.showDialogCosts(
    action: AdapterCallback,
    model: AdapterItems.RowItem,
    callback: (model: AdapterItems.RowItem) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext())
    val binding = bindingDialog(requireContext(), SampleDialogOneBinding::inflate)

    dialog.setContentView(binding.root)

    binding.bottomSheetEditCount.setText(model.count.round2String())
    binding.bottomSheetEditPrice.setText(model.price.round2String())

    binding.bottomSheetEditPrice.setOnEditorActionListener { _, editorInfo, _ ->
        if (editorInfo == EditorInfo.IME_ACTION_DONE) {
            val count = binding.bottomSheetEditCount.text.round2Double() ?: model.count
            val price = binding.bottomSheetEditPrice.text.round2Double() ?: model.price
            callback(model.copy(count = count, price = price))
            dialog.dismiss()
            true
        } else
            false
    }
    dialog.setOnShowListener {
        when (action) {
            is AdapterCallback.RowPrice -> {
                showKeyboardWithDelay(binding.bottomSheetEditPrice)
                binding.bottomSheetEditPrice.selectAll()
            }
            is AdapterCallback.RowCount -> {
                showKeyboardWithDelay(binding.bottomSheetEditCount)
                binding.bottomSheetEditCount.selectAll()
            }
            else -> {}
        }
    }
    dialog.show()
}

