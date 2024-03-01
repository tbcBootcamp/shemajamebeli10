package com.example.shemajamebelin10.domain.usecase

import com.example.shemajamebelin10.domain.repository.AccountsRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(private val repository: AccountsRepository) {
    suspend operator fun invoke() = repository.getAccount()
}