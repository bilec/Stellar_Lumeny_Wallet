package com.example.stellarlumenywallet.adapters.balances

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stellarlumenywallet.databinding.ItemBalanceBinding
import com.example.stellarlumenywallet.db.entities.Balance

class BalancesAdapter: RecyclerView.Adapter<BalancesViewHolder>() {
    var balances = listOf<Balance>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalancesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBalanceBinding.inflate(layoutInflater, parent, false)
        return BalancesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BalancesViewHolder, position: Int) {
        holder.bindView(balances[position])
    }

    override fun getItemCount(): Int = balances.size

}