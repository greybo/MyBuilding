package com.greybot.mycosts.present.main

interface IMainExploreRouter {
    fun fromExploreToFolder(objectId: String,pathName: String)
    fun fromExploreToAddFolder(path: String?)
    fun fromExploreToEditFolder(objectId: String)
}