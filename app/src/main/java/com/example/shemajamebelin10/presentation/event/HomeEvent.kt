package com.example.shemajamebelin10.presentation.event

sealed class HomeEvent {
    data object ResetError : HomeEvent()
    data object GetCurrency : HomeEvent()
}