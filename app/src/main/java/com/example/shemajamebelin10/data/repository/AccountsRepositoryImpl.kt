package com.example.shemajamebelin10.data.repository

import com.example.shemajamebelin10.data.common.HandleResponse
import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.data.mapper.base.asResource
import com.example.shemajamebelin10.data.mapper.toDomain
import com.example.shemajamebelin10.data.service.AccountApiService
import com.example.shemajamebelin10.domain.model.AccountDomainModel
import com.example.shemajamebelin10.domain.repository.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AccountsRepositoryImpl @Inject constructor(
    private val apiService: AccountApiService,
    private val handleResponse: HandleResponse
) :
    AccountsRepository {
    override suspend fun getAccounts(): Flow<Resource<List<AccountDomainModel>>> {
        return handleResponse.safeApiCall {
            apiService.getAccounts()
        }.asResource {
            it.map { it.toDomain() }
        }
    }

    override suspend fun getAccount(): Flow<Resource<AccountDomainModel>> {
        return handleResponse.safeApiCall {
            apiService.getAccount()
        }.asResource {
            it.toDomain()
        }
    }
}
