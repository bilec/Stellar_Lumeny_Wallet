package com.example.stellarlumenywallet.adapters.balances

import androidx.recyclerview.widget.RecyclerView
import com.example.stellarlumenywallet.databinding.ItemBalanceBinding
import com.example.stellarlumenywallet.db.entities.Balance

class BalancesViewHolder(private val binding: ItemBalanceBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindView(balance: Balance) {
        binding.assetType.text = balance.assetType
        binding.ammount.text = balance.balance
    }
}