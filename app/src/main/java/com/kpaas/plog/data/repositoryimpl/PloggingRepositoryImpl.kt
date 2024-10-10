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
import org.json.JSONObject
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
        file: File
    ): Result<String> {
        return runCatching {
            val jsonBody = JSONObject().apply {
                put("startRoadAddr", startRoadAddr)
                put("endRoadAddr", endRoadAddr)
                put("ploggingTime", ploggingTime)
            }.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val filePart = file.let {
                val requestBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("file", it.name, requestBody)
            }

            ploggingDataSource.postPloggingProof(
                jsonBody,
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