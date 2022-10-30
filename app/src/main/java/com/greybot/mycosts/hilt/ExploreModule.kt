package com.greybot.mycosts.hilt

import com.greybot.mycosts.data.repository.ExploreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ExploreModule {

    @Singleton
    @Provides
    fun provideExploreRepo(): ExploreRepository {
        return ExploreRepository()
    }
}