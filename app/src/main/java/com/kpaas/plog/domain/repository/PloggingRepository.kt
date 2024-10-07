package com.kpaas.plog.domain.repository

import java.io.File

interface PloggingRepository {
    suspend fun postPloggingProof(
        startRoadAddr: String,
        endRoadAddr: String,
        ploggingTime: String,
        proofImage: File
    ): Result<String>
}