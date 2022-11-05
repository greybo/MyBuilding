package com.greybot.mycosts.present.main

interface IMainExploreRouter {
    fun fromExploreToFolder(objectId: String)
    fun fromExploreToAddFolder()
    fun fromExploreToEditFolder(objectId: String)
}