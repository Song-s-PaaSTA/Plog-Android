package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.ProfileDataSource
import com.kpaas.plog.data.datasourceimpl.ProfileDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.ProfileRepositoryImpl
import com.kpaas.plog.data.service.ProfileApiService
import com.kpaas.plog.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): ProfileApiService = retrofit.create(ProfileApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesProfileDataSource(dataSourceImpl: ProfileDataSourceImpl): ProfileDataSource
    }
}