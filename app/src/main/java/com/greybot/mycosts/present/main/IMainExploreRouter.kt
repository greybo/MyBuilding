package com.greybot.mycosts.present.main

interface IMainExploreRouter {
    fun fromExploreToFolder(pathName: String)
    fun fromExploreToAddFolder(path: String?)
    fun fromExploreToEditFolder(objectId: String)
}