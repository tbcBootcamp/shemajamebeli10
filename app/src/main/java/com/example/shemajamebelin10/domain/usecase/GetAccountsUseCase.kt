package com.example.shemajamebelin10.domain.usecase

import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.domain.model.AccountDomainModel
import com.example.shemajamebelin10.domain.repository.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAccountsUseCase @Inject constructor(private val repository: AccountsRepository) {

    suspend operator fun invoke(): Flow<Resource<List<AccountDomainModel>>> {
        return repository.getAccounts()
    }
}