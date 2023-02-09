package com.greybot.mycosts.present.main

interface IMainExploreRouter {
    fun fromExploreToFolder(objectId: String)
    fun fromExploreToAddFolder(id: String)
    fun fromExploreToEditFolder(objectId: String)
}