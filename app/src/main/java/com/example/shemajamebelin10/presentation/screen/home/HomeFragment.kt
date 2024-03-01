package com.example.shemajamebelin10.presentation.screen.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.shemajamebelin10.databinding.FragmentHomeBinding
import com.example.shemajamebelin10.presentation.base.BaseFragment
import com.example.shemajamebelin10.presentation.event.HomeEvent
import com.example.shemajamebelin10.presentation.model.AccountUiModel
import com.example.shemajamebelin10.presentation.screen.home.bottomSheet.fromAccount.AccountFromBottomSheetFragment
import com.example.shemajamebelin10.presentation.screen.home.bottomSheet.toAccount.AccountToBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AccountFromBottomSheetFragment.OnAccountSelectedListener,
    AccountToBottomSheetFragment.OnAccountDetailsFetchedListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun setUp() {
        bindCurrency()
    }

    override fun setUpListeners() {
        bindToBottomSheet()
        bindFromBottomSheet()
    }

    override fun setUpObservers() {
        bindCurrencyFlow()
    }

    private fun bindCurrency() {
        homeViewModel.onEvent(HomeEvent.GetCurrency)
    }

    private fun bindFromBottomSheet() {
        binding.btnFromAccount.setOnClickListener {
            val bottomSheetFragment = AccountFromBottomSheetFragment().apply {
                accountSelectedListener = this@HomeFragment
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun bindToBottomSheet() {
        binding.btnToAccount.setOnClickListener {
            val bottomSheetFragment = AccountToBottomSheetFragment().apply {
                accountDetailsFetchedListener = this@HomeFragment
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun bindCurrencyFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.currencyFlow.collect {

                }
            }
        }
    }

    override fun onAccountSelected(bankAccount: AccountUiModel) {
        Log.d("HomeFragment", "Selected Account: ${bankAccount.accountName}, Number: ${bankAccount.accountNumber}")
        handleAccountSelected(account = bankAccount)
    }

    override fun onAccountDetailsFetched(account: AccountUiModel) {
        Log.d("HomeFragment", "Account Details Fetched: ${account.accountName}")
        handleRecipientAccountSelected(account = account)
    }

    private fun handleAccountSelected(account: AccountUiModel) {
        with(binding) {
            fromContainer.visibility = View.VISIBLE
            tvCardName.text = account.accountName
            tvNumber.text = account.accountNumber
            tvType.text = account.valuteType
            tvCardType.text = account.cardType
        }
    }

    private fun handleRecipientAccountSelected(account: AccountUiModel) {
        with(binding) {
            toContainer.visibility = View.VISIBLE
            tvCardNameTo.text = account.accountName
            tvNumberTo.text = account.accountNumber
            tvTypeTo.text = account.valuteType
            tvCardTypeTo.text = account.cardType
        }
    }
}

