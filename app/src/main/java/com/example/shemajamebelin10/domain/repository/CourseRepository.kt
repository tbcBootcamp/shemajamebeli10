package com.example.shemajamebelin10.domain.repository

import com.example.shemajamebelin10.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface CourseRepository{
    suspend fun getCourse() : Flow<Resource<Double>>
}