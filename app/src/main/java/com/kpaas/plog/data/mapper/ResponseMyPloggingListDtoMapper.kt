package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.PloggingList
import com.kpaas.plog.domain.entity.MyPloggingListEntity

fun PloggingList.toMyPloggingListEntity() = MyPloggingListEntity(
    startRoadAddr = startRoadAddr,
    endRoadAddr = endRoadAddr,
    ploggingTime = ploggingTime,
    ploggingImgUrl = ploggingImgUrl,
)