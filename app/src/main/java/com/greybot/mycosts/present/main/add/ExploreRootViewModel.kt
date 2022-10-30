package com.greybot.mycosts.present.main.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.Explore
import com.greybot.mycosts.data.repository.ExploreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExploreRootViewModel @Inject constructor(private val exploreRepo: ExploreRepository) :
    CompositeViewModel() {

    fun addFolderRoot(name: String?) {
        if (name != null) {
            val explore = Explore(name, Date().time)
            exploreRepo.addFolderRoot(explore)
        }
    }
}