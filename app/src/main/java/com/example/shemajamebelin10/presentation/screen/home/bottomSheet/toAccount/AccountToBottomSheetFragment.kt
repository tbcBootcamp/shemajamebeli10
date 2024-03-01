package com.example.shemajamebelin10.presentation.screen.home.bottomSheet.toAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.shemajamebelin10.databinding.FragmentToAccountBottomSheetBinding
import com.example.shemajamebelin10.presentation.event.ToEvent
import com.example.shemajamebelin10.presentation.model.AccountUiModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountToBottomSheetFragment : BottomSheetDialogFragment() {

    interface OnAccountDetailsFetchedListener {
        fun onAccountDetailsFetched(account: AccountUiModel)
    }

    var accountDetailsFetchedListener: OnAccountDetailsFetchedListener? = null

    private var _binding: FragmentToAccountBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val toViewModel : AccountToViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentToAccountBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUserAccount()
        bindToFlow()
    }

    private fun bindUserAccount() {
        with(binding) {
            btnSubmit.setOnClickListener {
                toViewModel.onEvent(ToEvent.GetUserAccount(cardNumber = "1234 5678 9012 3456 7890 123", id = "ABCD1234567", number = "123-456-789"))

            }
        }
    }

    private fun bindToFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                toViewModel.toFlow.collect { state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state : ToState) {
        state.error?.let { message ->
            handleErrorMessage(errorMessage = message)
            toViewModel.onEvent(ToEvent.ResetError)
        }

        state.accountList?.let { account ->
            accountDetailsFetchedListener?.onAccountDetailsFetched(account)
            dismiss()
        }
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun handleSuccess() {
        Snackbar.make(binding.root, "Success!", Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}