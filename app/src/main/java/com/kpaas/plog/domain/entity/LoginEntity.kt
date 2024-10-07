package com.kpaas.plog.domain.entity

data class LoginEntity (
    val accessToken: String,
    val refreshToken: String,
    val isNewMember: Boolean
)