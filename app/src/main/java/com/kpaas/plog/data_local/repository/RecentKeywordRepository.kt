package com.kpaas.plog.data_local.repository

import com.kpaas.plog.data_local.dao.RecentKeywordDao
import com.kpaas.plog.data_local.entity.RecentKeywordEntity
import java.time.LocalDateTime
import javax.inject.Inject

class RecentKeywordRepository @Inject constructor(
    private val recentKeywordDao: RecentKeywordDao
) {
    suspend fun getRecentKeywords() = recentKeywordDao.getRecentKeywords()

    suspend fun deleteRecentKeyword(recentKeywordEntity: RecentKeywordEntity) =
        recentKeywordDao.deleteRecentKeyword(recentKeywordEntity)

    suspend fun insertRecentKeyword(recentKeywordEntity: RecentKeywordEntity) {
        if (recentKeywordDao.isKeywordExists(recentKeywordEntity.keyword)) {
            recentKeywordDao.updateKeywordCreatedTime(
                recentKeywordEntity.keyword,
                LocalDateTime.now().toString()
            )
            return
        }
        recentKeywordDao.insertSearchKeyword(recentKeywordEntity)
    }

    suspend fun deleteAllRecentKeywords() = recentKeywordDao.deleteAllRecentKeywords()
}