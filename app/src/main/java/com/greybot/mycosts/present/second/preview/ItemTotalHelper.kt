package com.greybot.mycosts.present.second.preview

import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.data.repository.row.FileDataSource
import javax.inject.Inject

//val objectId: String,
class ItemTotalHelper @Inject constructor(
    private val exploreSource: ExploreDataSource,
    private val rowSource: FileDataSource
) {


}