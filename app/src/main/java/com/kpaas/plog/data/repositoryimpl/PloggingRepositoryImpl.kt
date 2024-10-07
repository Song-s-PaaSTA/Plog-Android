package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.PloggingDataSource
import com.kpaas.plog.domain.repository.PloggingRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PloggingRepositoryImpl @Inject constructor(
    private val ploggingDataSource: PloggingDataSource
) : PloggingRepository {
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
            ).content.toString()
        }
    }
}