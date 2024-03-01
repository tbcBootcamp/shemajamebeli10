package com.example.shemajamebelin10.data.mapper.base

import com.example.shemajamebelin10.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T: Any, R: Any> Flow<Resource<T>>.asResource(mapper: (T) -> R): Flow<Resource<R>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Success -> Resource.Success(mapper(resource.data))
            is Resource.Error -> Resource.Error(resource.error)
            is Resource.Loading -> Resource.Loading(resource.loading)
        }
    }
}