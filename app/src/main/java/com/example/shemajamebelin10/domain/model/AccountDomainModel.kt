package com.example.shemajamebelin10.domain.model

data class AccountDomainModel(
    val id: Int,
    val accountName: String,
    val accountNumber: String?,
    val valuteType: String?,
    val cardType: String?,
    val balance: Int?,
    val cardLogo: String?
)