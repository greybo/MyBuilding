package com.greybot.mycosts.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import au.com.crownresorts.crma.extensions.gone
import au.com.crownresorts.crma.extensions.setGoneOrVisible
import com.google.android.material.appbar.AppBarLayout
import com.greybot.mycosts.R
import com.greybot.mycosts.databinding.ActionBarCustomNewBinding

interface ToolbarDelegateListener {
    var title: String?
    fun rightButtonEnable(enable: Boolean?, rightTextColor: Int = -1)
    fun rightButtonShow(show: Boolean, text: String, textColor: Int)
    fun rightIcon(resId: Int? = 0)
}

class AppToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr), ToolbarDelegateListener {

    private val binding: ActionBarCustomNewBinding =
        ActionBarCustomNewBinding.inflate(LayoutInflater.from(context), this, true)

    private var rightButton: ((Int) -> Unit)? = null

    fun getBuilder(): Builder {
        return Builder()
    }

    override var title: String?
        get() = binding.customToolbar.title.toString()
        set(value) {
            binding.customToolbar.title = value
        }

    private fun setThemeType(enum: Int): ToolbarThemeStyle {
        val theme = when (enum) {
            0 -> ToolbarThemeStyle.Light
            1 -> ToolbarThemeStyle.Dark
            2 -> ToolbarThemeStyle.LightWithCrossCancel
            3 -> ToolbarThemeStyle.Gold
            4 -> ToolbarThemeStyle.LightWithoutBack
            else -> ToolbarThemeStyle.Light
        }

        setBgColor(theme.bgColor)
        setTextColor(theme.titleColor)
        if (theme.homeIconId != -1)
            setNavIcon(ContextCompat.getDrawable(context, theme.homeIconId))
        return theme
    }

    private fun setBgColor(bgColor: Int) {
        binding.customToolbar.setBackgroundColor(ContextCompat.getColor(context, bgColor))
    }

    private fun setTextColor(textColor: Int) {
        val color = ContextCompat.getColor(context, textColor)
        binding.customToolbar.setTitleTextColor(color)
        binding.customToolbarButtonRight.setTextColor(color)
    }

    private fun setRightButton(string: String?) {
        binding.customToolbarButtonRight.text = string
    }

    private fun setNavIcon(drawable: Drawable?) {
        binding.customToolbar.navigationIcon = drawable
    }

    private fun setModel(model: ToolbarModel): ToolbarDelegateListener {
        binding.customToolbar.setBackgroundColor(
            ContextCompat.getColor(
                context,
                model.theme.bgColor
            )
        )

        model.homeBtnCallback?.let { callback ->
            binding.customToolbar.navigationIcon = getDrawable(context, model.theme.homeIconId)
            binding.customToolbar.setNavigationOnClickListener {
                callback("back")
            }
        }
        binding.customToolbar.setTitleTextColor(
            ContextCompat.getColor(context, model.theme.titleColor)
        )
        binding.customToolbar.title = if (model.titleResId > 0) {
            resources.getString(model.titleResId)
        } else model.title

        val btnName = if (model.rightBtnNameResId != -1) {
            resources.getString(model.rightBtnNameResId)
        } else {
            model.rightBtnName
        }

        btnName?.let {
            binding.customToolbarButtonRight.text = it
        } ?: binding.customToolbarButtonRight.gone()

        model.rightBtnCallback?.let { callback ->
            binding.customToolbarButtonRight.setOnClickListener {
                callback(binding.customToolbarButtonRight)
            }
        }
        model.rightIconBtnCallback?.let {
            rightButton = model.rightIconBtnCallback
        } ?: binding.customToolbarIconRight.gone()


        rightButtonEnable(model.rightBtnEnabled, model.theme.titleColor)

        binding.customToolbarButtonRight.isAllCaps = model.rightBtnAllCaps
        if (!model.rightBtnAllCaps) {
            binding.customToolbarButtonRight.textSize =
                binding.root.resources.getDimension(R.dimen.trouble_text_size)
        }
        binding.crownToolbarShadow.setGoneOrVisible(model.shadow)
        invalidate()
        requestLayout()

        return this
    }

    override fun rightButtonEnable(enable: Boolean?, rightTextColor: Int) {
        binding.customToolbarButtonRight.isEnabled = enable ?: true
        val color = when (enable) {
            true -> ContextCompat.getColor(context, R.color.onboard_gradient_start)
            false -> ContextCompat.getColor(context, R.color.black_disable)
            else -> ContextCompat.getColor(context, rightTextColor)
        }
        binding.customToolbarButtonRight.setTextColor(color)
    }

    override fun rightButtonShow(show: Boolean, text: String, textColor: Int) {
        binding.customToolbarButtonRight.setGoneOrVisible(show)
        binding.customToolbarButtonRight.setTextColor(textColor)
        binding.customToolbarButtonRight.text = text
    }

    override fun rightIcon(resId: Int?) {
        val id = if (resId != null && resId > 0) {
            resId
        } else R.drawable.ic_action_menu
        binding.customToolbarIconRight.setImageResource(id)
        binding.customToolbarIconRight.setOnClickListener {
            rightButton?.invoke(id)
        }
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.AppToolbarView,
            defStyleAttr,
            0/*defStyleRes*/
        )
        try {
            val title = (a.getString(R.styleable.AppToolbarView_title))
            val rightButton = (a.getString(R.styleable.AppToolbarView_rightButton))
            val navIcon = (a.getDrawable(R.styleable.AppToolbarView_navIcon))
            val theme = (a.getInt(R.styleable.AppToolbarView_themeType, 0))

            if (isInEditMode) {
                this@AppToolbarView.title = title
                setThemeType(theme)
                setRightButton(rightButton)
                navIcon?.let { setNavIcon(it) }
            }
//            setTitle(AppToolsAttrs.getString(a, isInEditMode, R.styleable.CrownToolbarView_app_title, R.styleable.CrownToolbarView_tools_title))
        } finally {
            a.recycle()
        }
    }

    inner class Builder {

        private var title: String? = null
        private var titleResId: Int = -1
        private var rightBtnName: String? = null
        private var rightBtnNameResId: Int = -1
        private var homeBtnCallback: ((String) -> Unit)? = null
        private var rightBtnCallback: ((View) -> Unit)? = null
        private var rightIconBtnCallback: ((Int) -> Unit)? = null
        private var theme: ToolbarThemeStyle = ToolbarThemeStyle.Light
        private var rightBtnEnabled: Boolean? = null
        private var rightBtnAllCaps: Boolean = true
        private var shadow: Boolean? = null

        fun title(title: String?) = apply { this.title = title }
        fun title(resId: Int) = apply { this.titleResId = resId }
        fun theme(t: ToolbarThemeStyle?) = apply { t?.let { this.theme = it } }
        fun rightButtonName(name: String?) = apply { this.rightBtnName = name }
        fun rightButtonName(resId: Int) = apply { this.rightBtnNameResId = resId }
        fun rightBtnAllCaps(allCaps: Boolean = true) = apply { this.rightBtnAllCaps = allCaps }
        fun rightBtnEnabled(isEnable: Boolean?) =
            apply { isEnable?.let { this.rightBtnEnabled = it } }

        fun homeCallback(callback: ((String) -> Unit)?) = apply { this.homeBtnCallback = callback }
        fun rightCallback(callback: ((View) -> Unit)?) = apply { this.rightBtnCallback = callback }
        fun rightIconCallback(callback: ((Int) -> Unit)?) =
            apply { this.rightIconBtnCallback = callback }

        fun shadow(isShadow: Boolean?) = apply { this.shadow = isShadow }

        fun create(): ToolbarDelegateListener {
            val model = ToolbarModel(
                title = title,
                titleResId = titleResId,
                homeBtnCallback = homeBtnCallback,
                rightBtnCallback = rightBtnCallback,
                rightIconBtnCallback = rightIconBtnCallback,
                rightBtnName = rightBtnName,
                rightBtnNameResId = rightBtnNameResId,
                theme = theme,
                rightBtnEnabled = rightBtnEnabled,
                rightBtnAllCaps = rightBtnAllCaps,
                shadow = shadow ?: false
            )
            return setModel(model)
        }
    }
}


data class ToolbarModel(
    val title: String? = null,
    val titleResId: Int = -1,
    val homeBtnCallback: ((String) -> Unit)? = null,
    val rightBtnCallback: ((View) -> Unit)? = null,
    val rightIconBtnCallback: ((Int) -> Unit)? = null,
    val rightBtnName: String? = null,
    val rightBtnNameResId: Int = -1,
    val theme: ToolbarThemeStyle = ToolbarThemeStyle.Light,
    val rightBtnEnabled: Boolean? = null,
    val rightBtnAllCaps: Boolean = true,
    val shadow: Boolean = false
)

sealed class ToolbarThemeStyle(val homeIconId: Int, val bgColor: Int, val titleColor: Int) {
    object Dark : ToolbarThemeStyle(
        R.drawable.ic_arrow_back_white_24dp,
        R.color.black,
        titleColor = R.color.white
    )

    object Light :
        ToolbarThemeStyle(R.drawable.ic_arrow_back_black_24dp, R.color.white, R.color.black)

    object LightWithCrossCancel :
        ToolbarThemeStyle(R.drawable.ic_pin_close, R.color.white, R.color.black)

    object Gold : ToolbarThemeStyle(
        R.drawable.ic_arrow_back_white_24dp,
        R.color.onboard_gradient_start,
        R.color.white
    )

    object LightWithoutBack : ToolbarThemeStyle(-1, R.color.white, R.color.black)
}

