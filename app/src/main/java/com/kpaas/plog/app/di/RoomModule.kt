package com.kpaas.plog.app.di

import android.content.Context
import com.kpaas.plog.data_local.LocalDatabase
import com.kpaas.plog.data_local.dao.RecentKeywordDao
import com.kpaas.plog.data_local.repository.RecentKeywordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideRecentKeywordDao(localDatabase: LocalDatabase): RecentKeywordDao {
        return localDatabase.searchKeywordDao()
    }

    @Provides
    @Singleton
    fun provideRecentKeywordRepository(recentKeywordDao: RecentKeywordDao): RecentKeywordRepository {
        return RecentKeywordRepository(recentKeywordDao)
    }
}