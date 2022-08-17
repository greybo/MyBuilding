package au.com.crownresorts.crma.extensions

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import au.com.crownresorts.crma.utility.getSafeColor
import com.greybot.mycosts.R
import java.util.regex.Pattern


fun String.capitalizingFirstLetter(): String {
    return this[0].uppercase() + this.lowercase().substring(1)
}

fun String.capitalizeWords(): String {
    var result = ""
    for (word in this.split(" ")) {
        result = result.plus(word.lowercase().capitalize()).plus(" ")
    }

    return result
}


/**
Truncates the string to the specified length number of characters and appends an optional trailing string if longer.

- Parameter length: A `String`.
- Parameter trailing: A `String` that will be appended after the truncation.

- Returns: A `String` object.
 */
fun String.truncated(length: Int, trailing: String = "…"): String {
    if (this.length > length) {
        return substring(length) + trailing
    } else {
        return this
    }
}

@Suppress("DEPRECATION")
fun String.makeHTML(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String?.notNull(): String {
    return this?.let { it } ?: ""
}

fun String?.isMatches(pattern: Pattern): Boolean {
    return this?.let { pattern.matcher(it).find() } ?: false
}

fun TextView.setSpanDefault(
    text: String,
    span: String?,
    underline: Float = 3f,
    callback: (String) -> Unit
) = setSpanCustom(text, span, underlineThickness = underline, callback = callback)

fun TextView.setSpanLogin(
    text: String,
    span: String?,
    callback: (String) -> Unit
) = setSpanCustom(text, span, underlineThickness = 0F, callback = callback)

private fun TextView.setSpanCustom(
    text: String,
    span: String?,
    colorSpanId: Int = R.color.tint_primary,
    underlineThickness: Float = 3F,
    callback: ((String) -> Unit)? = null
) {
    if (!span.isNullOrEmpty() && text.contains(span)) {
        val ss = SpannableString(text)
        val colorSpan = getSafeColor(context, colorSpanId)
        val clickableSpan = object : AbsClickableSpan(colorSpan, underlineThickness) {
            override fun onClick(widget: View) {
                callback?.invoke(span)
            }
        }
        val start = text.indexOf(span)
        val end = start + span.length
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        this.text = ss
        this.movementMethod = LinkMovementMethod.getInstance()
        this.highlightColor = Color.TRANSPARENT
    } else {
        this.text = text
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