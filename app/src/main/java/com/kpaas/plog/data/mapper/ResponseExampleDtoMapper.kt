package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.ResponseExampleDto
import com.kpaas.plog.domain.entity.ExampleEntity

fun ResponseExampleDto.toExampleEntity() = ExampleEntity(
    email, firstName, avatar
)