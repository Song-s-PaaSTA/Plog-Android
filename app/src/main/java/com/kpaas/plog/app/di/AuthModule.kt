package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.AuthDataSource
import com.kpaas.plog.data.datasourceimpl.AuthDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.AuthRepositoryImpl
import com.kpaas.plog.data.service.AuthApiService
import com.kpaas.plog.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesAuthDataSource(dataSourceImpl: AuthDataSourceImpl): AuthDataSource
    }
}