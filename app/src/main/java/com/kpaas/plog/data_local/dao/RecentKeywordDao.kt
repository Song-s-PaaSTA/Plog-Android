package com.kpaas.plog.data_local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kpaas.plog.data_local.entity.RecentKeywordEntity

@Dao
interface RecentKeywordDao {

    @Insert
    suspend fun insertSearchKeyword(recentKeywordEntity: RecentKeywordEntity)

    @Query("SELECT * FROM recent_keyword ORDER BY createdTime DESC")
    suspend fun getRecentKeywords(): List<RecentKeywordEntity>

    @Delete
    suspend fun deleteRecentKeyword(recentKeywordEntity: RecentKeywordEntity)

    @Query("DELETE FROM recent_keyword")
    suspend fun deleteAllRecentKeywords()

    @Query("UPDATE recent_keyword SET createdTime = :newCreatedTime WHERE name = :name")
    suspend fun updateKeywordCreatedTime(name: String, newCreatedTime: String)

    @Query("SELECT EXISTS(SELECT 1 FROM recent_keyword WHERE name = :name)")
    suspend fun isKeywordExists(name: String): Boolean

}