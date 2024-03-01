package com.example.shemajamebelin10.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebelin10.data.common.Resource
import com.example.shemajamebelin10.domain.usecase.GetCourseUseCase
import com.example.shemajamebelin10.presentation.event.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCourseUseCase: GetCourseUseCase) :
    ViewModel() {

    private val _currencyFlow = MutableStateFlow(CurrencyState())
    val currencyFlow: StateFlow<CurrencyState> = _currencyFlow.asStateFlow()

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetCurrency -> getCurrency()
                is HomeEvent.ResetError -> setError(null)
                else -> {}
            }
        }
    }

    private suspend fun getCurrency() {
        getCourseUseCase().collect { resource ->
            when (resource) {
                is Resource.Error ->
                    setError(resource.error)

                is Resource.Loading ->
                    _currencyFlow.value = CurrencyState(isLoading = true)

                is Resource.Success -> {
                    Log.d("ViewModel", resource.data.toString())
                    _currencyFlow.value = CurrencyState(currency = resource.data, isLoading = false)
                }
            }
        }
    }

    private fun setError(error: String?) {
        viewModelScope.launch {
            _currencyFlow.update { currentState -> currentState.copy(error = error) }
        }
    }
}

data class CurrencyState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currency: Double? = null
)