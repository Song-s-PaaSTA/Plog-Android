package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.ReportDataSource
import com.kpaas.plog.data.datasourceimpl.ReportDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.ReportRepositoryImpl
import com.kpaas.plog.data.service.ReportApiService
import com.kpaas.plog.domain.repository.ReportRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReportModule {
    @Provides
    @Singleton
    fun provideReportApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): ReportApiService = retrofit.create(ReportApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsReportRepository(repositoryImpl: ReportRepositoryImpl): ReportRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesReportDataSource(dataSourceImpl: ReportDataSourceImpl): ReportDataSource
    }
}