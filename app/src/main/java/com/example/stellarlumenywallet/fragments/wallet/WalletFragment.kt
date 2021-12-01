package com.example.stellarlumenywallet.fragments.wallet

import android.os.Bundle
import android.transition.Visibility
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.adapters.balances.BalancesAdapter
import com.example.stellarlumenywallet.databinding.FragmentWalletBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class WalletFragment: Fragment() {
    private lateinit var binding: FragmentWalletBinding
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
        val accountRepository by lazy { AccountRepository(database.accountDao()) }
        val activeAccountRepository by lazy { ActiveAccountRepository(database.activeAccount()) }
        val viewModelFactory = WalletViewModelFactory(transactionRepository, accountRepository, activeAccountRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WalletViewModel::class.java]

        val adapter = BalancesAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.allTransactions.observe(this) {
            val newTransactions = it.takeLast(3)
            when (newTransactions.size) {
                1 -> {
                    binding.firstTransaction.text = newTransactions[0].toString()
                    binding.secondTransaction.visibility = View.GONE
                    binding.thirdTransaction.visibility = View.GONE
                }
                2 -> {
                    binding.firstTransaction.text = newTransactions[0].toString()
                    binding.secondTransaction.text = newTransactions[1].toString()
                    binding.thirdTransaction.visibility = View.GONE
                }
                3 -> {
                    binding.firstTransaction.text = newTransactions[0].toString()
                    binding.secondTransaction.text = newTransactions[1].toString()
                    binding.thirdTransaction.text = newTransactions[2].toString()
                }
            }
        }

        viewModel.allAccountsWithBalances.observe(this) {
            adapter.balances
            adapter.notifyDataSetChanged()
        }

        // binding.swipeRefreshLayout.setOnRefreshListener { runBlocking(Dispatchers.IO) { launch { } } }
        // withContext(Dispatchers.Main) { binding.swipeRefreshLayout.isRefreshing = false }

        return binding.root
    }
}