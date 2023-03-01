package com.greybot.mycosts.components.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ActionButtonType(val icon: ImageVector) {
    Delete(Icons.Default.Delete),
    Menu(Icons.Default.Menu),
    Back(Icons.Default.ArrowBack)
}

interface IToolbarModel {
    var title: String?
    var homeAction: ActionToolbar?
    var rightAction: ActionToolbar?
    var colorsDefault: ColorToolbar
    var callback: (ActionButtonType) -> Unit
}

data class ToolbarModel(
    override var title: String? = "My Costs",
    override var homeAction: ActionToolbar? = homeActionDefault(),
    override var rightAction: ActionToolbar? = rightActionDefault(),
    override var colorsDefault: ColorToolbar = toolbarColorDefault(),
    override var callback: (ActionButtonType) -> Unit = {}
) : IToolbarModel {
}

data class ActionToolbar(val type: ActionButtonType, val color: Color? = null)

data class ColorToolbar(
    val backgroundColor: Color = Color.White,
    val contentColor: Color = Color.Black
)

fun toolbarColorDefault(
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
): ColorToolbar {
    return ColorToolbar(backgroundColor, contentColor)
}

fun homeActionDefault(
    iconType: ActionButtonType? = ActionButtonType.Back,
    color: Color? = null
): ActionToolbar? {
    return iconType?.let { ActionToolbar(type = it, color = color) }
}

fun rightActionDefault(
    iconType: ActionButtonType? = ActionButtonType.Menu,
    color: Color? = null
): ActionToolbar? {
    return iconType?.let { ActionToolbar(type = it, color = color) }
}