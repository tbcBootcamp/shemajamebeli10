package com.example.shemajamebelin10.data.mapper

import com.example.shemajamebelin10.data.model.AccountDto
import com.example.shemajamebelin10.domain.model.AccountDomainModel


fun AccountDto.toDomain(): AccountDomainModel = AccountDomainModel(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType= valuteType,
    cardType=cardType,
    balance=balance,
    cardLogo=cardLogo
)