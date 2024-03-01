package com.example.shemajamebelin10.presentation.screen.home.bottomSheet.fromAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.domain.usecase.GetAccountsUseCase
import com.example.shemajamebelin10.presentation.event.FromEvent
import com.example.shemajamebelin10.presentation.event.HomeEvent
import com.example.shemajamebelin10.presentation.mapper.toPresentation
import com.example.shemajamebelin10.presentation.model.AccountUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountFromViewModel @Inject constructor(private val GetAccountsUseCase: GetAccountsUseCase) :
    ViewModel() {

    private val _fromState = MutableStateFlow(FromState())
    val fromState: StateFlow<FromState> = _fromState.asStateFlow()

    fun onEvent(event: FromEvent) {
        viewModelScope.launch {
            when (event) {
                is FromEvent.GetUserAccounts -> getBankAccounts()
                is FromEvent.ResetError -> setError(null)
            }
        }
    }

    private suspend fun getBankAccounts() {
        GetAccountsUseCase().collect { resource ->
            when (resource) {
                is Resource.Error ->
                    setError(resource.error)

                is Resource.Loading ->
                    _fromState.value = FromState(isLoading = true)

                is Resource.Success -> {
                    Log.d("ViewModel", resource.data.toString())
                    val accounts = resource.data.map { it.toPresentation() }
                    _fromState.value = FromState(accountList = accounts, isLoading = false)
                }
            }
        }
    }

    private fun setError(error: String?) {
        viewModelScope.launch {
            _fromState.update { currentState -> currentState.copy(error = error) }
        }
    }
}

data class FromState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val accountList: List<AccountUiModel>? = null
)