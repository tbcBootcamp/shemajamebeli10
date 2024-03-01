package com.example.shemajamebelin10.presentation.mapper

import com.example.shemajamebelin10.domain.model.AccountDomainModel
import com.example.shemajamebelin10.presentation.model.AccountUiModel


fun AccountDomainModel.toPresentation(): AccountUiModel = AccountUiModel(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType = valuteType,
    cardType = cardType,
    balance = balance,
    cardLogo = cardLogo
)