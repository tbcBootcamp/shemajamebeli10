package com.example.shemajamebelin10.presentation.model


data class AccountUiModel(
    val id: Int,
    val accountName: String,
    val accountNumber: String?,
    val valuteType: String?,
    val cardType: String?,
    val balance: Int?,
    val cardLogo: String?
)