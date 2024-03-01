package com.example.shemajamebelin10.presentation.event


sealed class FromEvent {
    data object ResetError : FromEvent()
    data object GetUserAccounts : FromEvent()
}