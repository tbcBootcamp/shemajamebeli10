package com.example.shemajamebelin10.domain.usecase

import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(private val courseRepository: CourseRepository) {
    suspend operator fun invoke() : Flow<Resource<Double>> {
        return courseRepository.getCourse()
    }
}