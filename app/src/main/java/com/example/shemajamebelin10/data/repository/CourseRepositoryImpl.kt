package com.example.shemajamebelin10.data.repository

import com.example.shemajamebelin10.data.common.HandleResponse
import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.data.mapper.base.asResource
import com.example.shemajamebelin10.data.service.CurrencyService
import com.example.shemajamebelin10.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val currencyService: CurrencyService
) : CourseRepository {
    override suspend fun getCourse(): Flow<Resource<Double>> {
        return handleResponse.safeApiCall { currencyService.getCurrentRate() }
            .asResource { courseDto ->
                courseDto.course
            }
    }
}