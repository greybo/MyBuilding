package com.greybot.mybuilding.ui.main

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.color
import au.com.crownresorts.crma.extensions.toast
import au.com.crownresorts.crma.utility.getSafeColor
import com.greybot.mybuilding.R
import com.greybot.mybuilding.base.BaseBindingFragment
import com.greybot.mybuilding.databinding.MainFragmentBinding

class MainFragment : BaseBindingFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "Which is already bold so nothing special"
        val textSpan = " nothing special"
        binding.mainTextView.setSpanText(text, textSpan){

        }
    }

    private fun TextView.setSpanText(text: String, textSpan: String, function: (View) -> Unit) {
        val clickable = object : AbsClickableSpan(getSafeColor(this.context, R.color.text_medium), 3f) {
            override fun onClick(v: View) {
                function.invoke(v)
                toast("message")
            }

        }
        val start = text.indexOf(textSpan)
        val end = start + textSpan.length

        val builder = SpannableStringBuilder().color(getSafeColor(this.context, R.color.purple_500)) { append(text) }
        builder.setSpan(clickable, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setText(builder)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }
}

abstract class AbsClickableSpan(private val lineColor: Int, private val underlineThickness: Float) : ClickableSpan() {
    override fun updateDrawState(ds: TextPaint) {
        try {
            ds.color = lineColor
            val method = TextPaint::class.java.getMethod("setUnderlineText", Int::class.java, Float::class.java)
            method.invoke(ds, lineColor, underlineThickness)
        } catch (e: Exception) {
            super.updateDrawState(ds)
            e.printStackTrace() // bad
        }
    }
}