package com.greybot.mycosts.present.second.add

import com.greybot.mycosts.AppCoordinator
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.Explore
import com.greybot.mycosts.data.repository.ExploreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FolderAddViewModel  @Inject constructor(private val exploreRepo: ExploreRepository): CompositeViewModel() {

    private val dataSource get() = AppCoordinator.shared.folderDataSource

    fun addFolder(name: String?, path: String?, time: Long) {
        if (name != null)
            dataSource.addFolder(name, path, time)
    }
    fun addFolderNew(name: String?,  time: Long) {
        if (name != null){
            val explore = Explore(name)
            exploreRepo.addFolder(explore)
        }
    }
}