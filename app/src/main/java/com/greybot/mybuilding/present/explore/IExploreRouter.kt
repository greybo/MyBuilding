package com.greybot.mybuilding.present.explore

interface IExploreRouter {
    fun fromFolderToFolder(pathName: String)
    fun fromExploreToFolder(pathName: String)
}