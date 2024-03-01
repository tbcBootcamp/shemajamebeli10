package com.example.shemajamebelin10.presentation.event

sealed class ToEvent {
    data class GetUserAccount(val cardNumber : String, val id: String, val number : String) : ToEvent()
    data object ResetError : ToEvent()

}