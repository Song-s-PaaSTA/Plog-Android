package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.PloggingDataSource
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.data.mapper.toLatLngEntity
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.domain.repository.PloggingRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PloggingRepositoryImpl @Inject constructor(
    private val ploggingDataSource: PloggingDataSource
) : PloggingRepository {
    override suspend fun getPloggingRoute(requestPloggingRouteDto: RequestPloggingRouteDto): Result<List<LatLngEntity>> {
        return runCatching {
            ploggingDataSource.getPloggingRoute(requestPloggingRouteDto).message?.coordinates?.map { it.toLatLngEntity() }
                ?: emptyList()
        }
    }

    override suspend fun postPloggingProof(
        startRoadAddr: String,
        endRoadAddr: String,
        ploggingTime: String,
        proofImage: File
    ): Result<String> {
        return runCatching {
            val startRoadAddrBody = startRoadAddr.toRequestBody("text/plain".toMediaTypeOrNull())
            val endRoadAddrBody = endRoadAddr.toRequestBody("text/plain".toMediaTypeOrNull())
            val ploggingTimeBody = ploggingTime.toRequestBody("text/plain".toMediaTypeOrNull())
            val filePart = proofImage.let {
                val requestBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("proofImage", it.name, requestBody)
            }

            ploggingDataSource.postPloggingProof(
                startRoadAddrBody,
                endRoadAddrBody,
                ploggingTimeBody,
                filePart
            ).message.toString()

        }.onFailure { throwable ->
            return when (throwable) {
                is IOException -> Result.failure(IOException(throwable.message))
                else -> Result.failure(throwable)
            }

        }
    }
}