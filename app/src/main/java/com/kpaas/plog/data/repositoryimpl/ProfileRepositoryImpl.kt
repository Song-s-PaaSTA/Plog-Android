package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.ProfileDataSource
import com.kpaas.plog.data.dto.response.PloggingList
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.mapper.toMyPloggingListEntity
import com.kpaas.plog.domain.entity.MyPloggingListEntity
import com.kpaas.plog.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Result<ResponseSignUpDto> {
        return runCatching {
            profileDataSource.getProfile().message ?: throw Exception("getProfile() failed")
        }
    }

    override suspend fun getPlogging(): Result<List<MyPloggingListEntity>> {
        return runCatching {
            profileDataSource.getPlogging().message?.ploggingList?.map { it.toMyPloggingListEntity() }
                ?: emptyList()
        }
    }
}