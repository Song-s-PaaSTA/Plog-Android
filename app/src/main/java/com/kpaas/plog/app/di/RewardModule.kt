package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.RewardDataSource
import com.kpaas.plog.data.datasourceimpl.RewardDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.RewardRepositoryImpl
import com.kpaas.plog.data.service.RewardApiService
import com.kpaas.plog.domain.repository.RewardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RewardModule {
    @Provides
    @Singleton
    fun provideRewardApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): RewardApiService = retrofit.create(RewardApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsRewardRepository(repositoryImpl: RewardRepositoryImpl): RewardRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesRewardDataSource(dataSourceImpl: RewardDataSourceImpl): RewardDataSource
    }
}