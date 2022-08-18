package com.greybot.mycosts.present.explore

interface IExploreRouter {
    fun fromExploreToFolder(pathName: String)
    fun fromExploreToAddFolder(path: String? = null)
}