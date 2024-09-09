package com.kpaas.plog.data_local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_keyword")
data class RecentKeywordEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var keyword: String,
    var address: String,
    var roadAddress: String,
    var createdTime: String = LocalDateTime.now().toString()
)