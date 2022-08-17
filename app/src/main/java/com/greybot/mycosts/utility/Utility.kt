package au.com.crownresorts.crma.utility

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Size
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.color
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import au.com.crownresorts.crma.extensions.gone
import au.com.crownresorts.crma.extensions.visible
import java.util.*


class Utils {
//    companion object {
//        fun documentsPath(): File = CrownApplication.instance.filesDir
//
//        fun getAppVersion(): String {
//            return CrownApplication.instance.packageManager.getPackageInfo(CrownApplication.instance.packageName, 0).versionName
//        }
//
//        fun isNetworkAvailable(): Boolean = hasInternetConnection()
//    }
}

fun String.containsItemFromList(list: List<String>): Boolean {
    list.forEach { if (this.contains(it, true)) return true }
    return false
}

/**
 * Scroll to internal view if it is not direct parent
 * @view must be direct or indirect child of scrollview!
 */
fun NestedScrollView.scrollToView(view: View) {
    // Get deepChild Offset
    val childOffset = Point()
    getDeepChildOffset(this, view.parent, view, childOffset)
    // Scroll to child.
    this.smoothScrollTo(0, childOffset.y)
}

/**
 * Get recursive y offset
 */
private fun getDeepChildOffset(mainParent: ViewGroup, parent: ViewParent, child: View, accumulatedOffset: Point) {
    val parentGroup = parent as ViewGroup
    accumulatedOffset.x += child.left
    accumulatedOffset.y += child.top
    if (parentGroup == mainParent) {
        return
    }
    getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
}

@Suppress("DEPRECATION")
fun String.getHtml(): String {
    return if (Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }.toString().trim()

}

//fun isRunningApp(): Boolean {
//    val context: Context = CrownApplication.instance.applicationContext
//    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
//
//    if (Build.VERSION.SDK_INT >= 23) {
//        val appTasks = activityManager?.appTasks
//        appTasks?.let {
//            for (task in appTasks) {
//                if (task.taskInfo?.baseActivity?.className == RootTabBarActivity::class.qualifiedName) return true
//            }
//        }
//    } else {
//        val runningTasks = activityManager?.getRunningTasks(Int.MAX_VALUE)
//        for (task in runningTasks!!) {
//            if (task.baseActivity?.className == RootTabBarActivity::class.qualifiedName) return true
//        }
//    }
//    return false
//}

fun isGamingContent(isOffer: Boolean?, isUser: Boolean): Boolean {
    return if (isOffer == null || !isOffer) {
        true
    } else isUser
}

@Suppress("unused", "DEPRECATION")
fun getSafeColor(context: Context, id: Int): Int {
    return try {
        ContextCompat.getColor(context, id)
    } catch (e: Exception) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ResourcesCompat.getColor(context.resources, id, null)
        } else {
            context.resources.getColor(id)
        }
    }
}

@Suppress("unused", "DEPRECATION")
fun Fragment.getColorSafe(id: Int): Int {
    val context: Context = this.context ?: return 0
    return try {
        ContextCompat.getColor(context, id)
    } catch (e: Exception) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ResourcesCompat.getColor(context.resources, id, null)
        } else {
            context.resources.getColor(id)
        }
    }
}

@Suppress("unused", "DEPRECATION")
fun Context.getColorSafe(id: Int): Int {
    return try {
        ContextCompat.getColor(this@getColorSafe, id)
    } catch (e: Exception) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ResourcesCompat.getColor(this@getColorSafe.resources, id, null)
        } else {
            this@getColorSafe.resources.getColor(id)
        }
    }
}

fun getColorListSafe(context: Context, resId: Int): ColorStateList {
    val colorInt = getSafeColor(context, resId)
    return ColorStateList.valueOf(colorInt)
}

fun TextView.setTextOrGone(text: String?, vararg views: View?) {
    if (!TextUtils.isEmpty(text)) {
        this.visibility = View.VISIBLE
        this.text = text
        views.iterator().forEach { it?.visibility = View.VISIBLE }
    } else {
        this.visibility = View.GONE
        views.iterator().forEach { it?.visibility = View.GONE }
    }
}

fun View.setTextOrGone(text: SpannableStringBuilder?) {
    if (!TextUtils.isEmpty(text)) {
        this.visible()
        when (this) {
            is TextView -> this.text = text
            is Button -> this.text = text
        }
    } else this.gone()
}

fun TextView.setTextOrGone(text: String? = null) {
    if (!TextUtils.isEmpty(text)) {
        this.visibility = View.VISIBLE
        this.text = text
    } else {
        this.visibility = View.GONE
    }
}

fun Button.setEnableOrGone(enabled: Boolean, text: String?) {
    if (enabled && !TextUtils.isEmpty(text)) {
        this.visibility = View.VISIBLE
        this.text = text
    } else this.visibility = View.GONE
}

fun calculateTextLines(context: Context, text: String, size: Float): Int {
    //Dirty crazy hack... we suggest width of the text as 85% of the screen width
    val width = (getScreenWidth(context) * 0.85).toInt()
    val textView = TextView(context)
    textView.text = text
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    textView.measure(widthMeasureSpec, heightMeasureSpec)
    return textView.lineCount
}

fun getScreenWidth(context: Context): Int {
    val displayMetrics = DisplayMetrics()
    (context as? Activity)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

data class SizeScreen(val width: Float, val height: Float)

fun sizeScreenMetrics(context: Context?): SizeScreen {
    return context?.let {
        val displayMetrics = context.resources.displayMetrics
        SizeScreen(
            displayMetrics.widthPixels.toFloat(),
            displayMetrics.heightPixels.toFloat()
        )
    } ?: SizeScreen(0f, 0f)
}

fun Context.screenMetrics() = Size(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)

fun View.getViewCoordinates(callback: (Rect) -> Unit) {
    post {
        callback.invoke(Rect(left, top, right, bottom))
    }
}

fun horizontalProportion(context: Context, span: Float): Int {
    val displayMetrics = context.resources.displayMetrics
    val width = displayMetrics.widthPixels
    return (width / span).toInt()
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

val String?.crownCapitalize: String
    get() = this?.trim()?.lowercase()?.replaceFirstChar(Char::titlecase)!!//capitalize() ?: ""

val String?.fullCapitalize: String
    get() = this?.trim()?.lowercase(Locale.ROOT)?.split(" ")?.joinToString(" ") { it.capitalize() }
        ?: ""

fun Triple<String, String, String>.crownCapitalize(): Triple<String, String, String> {
    return this.copy(first.fullCapitalize, second.fullCapitalize, third.fullCapitalize)
}

fun ImageView.imageFadeIn() {
    val animation = AlphaAnimation(0.0f, 1.0f)
    animation.duration = 800
    animation.fillAfter = true
    this.startAnimation(animation)
}


fun getSizeGalleryItem(context: Context): SizeScreen {
    val sizeScreen = sizeScreenMetrics(context)
    val width = sizeScreen.width * 0.9
    val height = sizeScreen.height * 0.75
    return SizeScreen(width.toFloat(), height.toFloat())
}

fun getSizeDetailGalleryItem(context: Context): SizeScreen {
    val sizeScreen = sizeScreenMetrics(context)
    val width = sizeScreen.width / 3.2
    val height = width
    return SizeScreen(width.toFloat(), height.toFloat())
}

//val String.sydneyUrlFix: String
//    get() = this.replace("~/", Configuration.shared.property.baseContentSydney)


fun View.getButtonName(): String {
    return when (this) {
        is TextView -> this.text.toString()
        is Button -> this.text.toString()
        else -> ""
    }
}
//
//fun forDebugBuild(callback: () -> Unit) {
//    if (BuildConfig.BUILD_TYPE == BuildTypes.DEBUG.value) callback()
//}
//
//fun forNoProdBuild(callback: () -> Unit) {
//    if (BuildConfig.BUILD_TYPE != BuildTypes.PROD.value) callback()
//}
//
//fun forDebugBuild(debug: () -> Unit, noDebug: () -> Unit) {
//    if (BuildConfig.BUILD_TYPE == BuildTypes.DEBUG.value) debug()
//    else noDebug()
//}

//fun vibrate(context: Context, length: Long = 200) {
//    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
//    if (vibrator?.hasVibrator() == true)
//        vibrator.vibrate(length)
//}

//fun getIntentUpdateApp(activity: Activity?): Intent? {
//    val packageManager = activity?.packageManager ?: return null
//    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_PACKAGE_NAME"))
//    return intent.resolveActivity(packageManager)?.let {
//        intent
//    } ?: with(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$APP_PACKAGE_NAME"))) {
//        this.resolveActivity(packageManager)?.let {
//            intent
//        }
//    }
//
////    try {
////        activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_PACKAGE_NAME")))
////    } catch (anfe: ActivityNotFoundException) {
////        activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$APP_PACKAGE_NAME")))
////    }
//}

fun String.toSpannableBuilder(context: Context, color: Int): SpannableStringBuilder {
    return context.toSpannableBuilder(mapOf(color to this))
}

fun Context.toSpannableBuilder(map: Map<Int, String>): SpannableStringBuilder {
    val spannable = SpannableStringBuilder()
    map.entries.map {
        spannable.color(ContextCompat.getColor(this, it.key)) { append(it.value) }
    }
    return spannable
}

fun String?.prepareName(): String? {
    return if (this.isNullOrBlank()) null else this.trim()
}