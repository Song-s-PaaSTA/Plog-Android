package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.TrashDataSource
import com.kpaas.plog.data.datasourceimpl.TrashDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.TrashRepositoryImpl
import com.kpaas.plog.data.service.TrashApiService
import com.kpaas.plog.domain.repository.TrashRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrashModule {
    @Provides
    @Singleton
    fun provideTrashApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): TrashApiService = retrofit.create(TrashApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsTrashRepository(repositoryImpl: TrashRepositoryImpl): TrashRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesTrashDataSource(dataSourceImpl: TrashDataSourceImpl): TrashDataSource
    }
}