package com.sample.videoplayer.di

import android.content.Context
import androidx.work.WorkManager
import com.sample.videoplayer.data.home.HomeService
import com.sample.videoplayer.domain.home.HomeRepository
import com.sample.videoplayer.domain.home.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Provides
    @Singleton
    fun provideHomeUseCase(homeRepository: HomeRepository): HomeUseCase {
        return HomeUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(@ApplicationContext context: Context): HomeRepository {
        return HomeService(context)
    }
}
