package com.example.shemajamebelin10.domain.repository

import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.domain.model.AccountDomainModel
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun getAccounts(): Flow<Resource<List<AccountDomainModel>>>
    suspend fun getAccount(): Flow<Resource<AccountDomainModel>>

}