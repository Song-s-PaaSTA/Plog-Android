package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.domain.entity.LoginEntity

fun ResponseLoginDto.toLoginEntity() = LoginEntity (
    accessToken = accessToken,
    refreshToken = refreshToken,
    isNewMember = isNewMember
)