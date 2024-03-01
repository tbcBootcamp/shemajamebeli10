package com.example.shemajamebelin10.presentation.screen.home.bottomSheet.toAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.domain.usecase.GetAccountUseCase
import com.example.shemajamebelin10.presentation.event.ToEvent
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
class AccountToViewModel @Inject constructor(private val getAccountUseCase: GetAccountUseCase) : ViewModel() {

    private val _toFlow = MutableStateFlow(ToState())
    val toFlow : StateFlow<ToState> = _toFlow.asStateFlow()

    fun onEvent(event: ToEvent) {
        viewModelScope.launch {
            when(event) {
                is ToEvent.GetUserAccount -> getBankAccountsDetails()
                is ToEvent.ResetError -> setError(null)
            }
        }
    }

    private suspend fun getBankAccountsDetails() {
        getAccountUseCase().collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _toFlow.value = ToState(isLoading = true)
                }
                is Resource.Error -> {
                    setError(resource.error)
                }
                is Resource.Success -> { resource.data.let { data ->
                    val accountView = data.toPresentation()
                    _toFlow.value = ToState(accountList = accountView)
                }
                }
            }
        }
    }
    private fun setError(error: String?) {
        viewModelScope.launch {
            _toFlow.update { currentState -> currentState.copy(error = error) }
        }
    }
}

data class ToState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val accountList: AccountUiModel? = null
)