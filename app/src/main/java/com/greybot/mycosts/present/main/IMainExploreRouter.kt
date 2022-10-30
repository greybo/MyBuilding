package com.greybot.mycosts.present.main

interface IMainExploreRouter {
    fun fromExploreToFolder(objectId: String,pathName: String)
    fun fromExploreToAddFolder()
    fun fromExploreToEditFolder(objectId: String)
}