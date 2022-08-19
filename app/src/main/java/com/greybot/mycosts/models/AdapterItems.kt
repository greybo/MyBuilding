package com.greybot.mycosts.models

sealed class AdapterItems {
    class ExploreItem(val name: String, val path: String) : AdapterItems()
    class AddContentItem(val name: String) : AdapterItems()
}
