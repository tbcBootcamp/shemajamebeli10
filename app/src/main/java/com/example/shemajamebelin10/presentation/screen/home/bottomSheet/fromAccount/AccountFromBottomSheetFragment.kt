package com.example.shemajamebelin10.presentation.screen.home.bottomSheet.fromAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.shemajamebelin10.databinding.FragmentFromAccountBottomSheetBinding
import com.example.shemajamebelin10.presentation.base.showSnackBar
import com.example.shemajamebelin10.presentation.event.FromEvent
import com.example.shemajamebelin10.presentation.model.AccountUiModel
import com.example.shemajamebelin10.presentation.screen.home.adapter.BankAccountsRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountFromBottomSheetFragment : BottomSheetDialogFragment() {

    interface OnAccountSelectedListener {
        fun onAccountSelected(bankAccount: AccountUiModel)
    }

    var accountSelectedListener: OnAccountSelectedListener? = null

    private var _binding: FragmentFromAccountBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val fromViewModel : AccountFromViewModel by viewModels()

    private lateinit var adapter: BankAccountsRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFromAccountBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRecyclerView()
        bindUserAccounts()
        bindFromFlow()
    }

    private fun bindUserAccounts() {
        fromViewModel.onEvent(FromEvent.GetUserAccounts)
    }

    private fun bindRecyclerView() {
        adapter = BankAccountsRecyclerAdapter { bankAccount ->
            handleClick(bankAccount)
        }
        binding.accountRv.adapter = adapter
    }
    private fun bindFromFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fromViewModel.fromState.collect { state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state : FromState) {
        state.error?.let {
            requireView().showSnackBar(it)
            fromViewModel.onEvent(FromEvent.ResetError)        }

        state.accountList?.let { accounts ->
            adapter.submitList(accounts)
        }
    }

    private fun handleClick(bankAccount: AccountUiModel) {
        accountSelectedListener?.onAccountSelected(bankAccount)
        dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}