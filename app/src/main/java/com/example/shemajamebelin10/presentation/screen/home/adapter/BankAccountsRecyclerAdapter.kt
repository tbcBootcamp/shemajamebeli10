package com.example.shemajamebelin10.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shemajamebelin10.databinding.ItemAccountsLayoutBinding
import com.example.shemajamebelin10.presentation.model.AccountUiModel


class BankAccountsRecyclerAdapter(private val onItemClick: (AccountUiModel) -> Unit): ListAdapter<AccountUiModel, BankAccountsRecyclerAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAccountsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }

    inner class ViewHolder(private val binding: ItemAccountsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: AccountUiModel) {
            with(binding) {
                tvCardName.text = account.accountName
                tvNumber.text = account.accountNumber
                tvType.text = account.valuteType
                tvCardType.text = account.cardType

                root.setOnClickListener { onItemClick(account) }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AccountUiModel>() {
        override fun areItemsTheSame(oldItem: AccountUiModel, newItem: AccountUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountUiModel, newItem: AccountUiModel): Boolean {
            return oldItem == newItem
        }
    }
}