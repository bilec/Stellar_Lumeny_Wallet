package com.example.stellarlumenywallet.fragments.wallet

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.api.StellarApi
import com.example.stellarlumenywallet.databinding.FragmentWalletBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WalletFragment: Fragment() {
    private lateinit var binding: FragmentWalletBinding
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
        val accountRepository by lazy { AccountRepository(database.accountDao()) }
        val viewModelFactory = WalletViewModelFactory(transactionRepository, accountRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WalletViewModel::class.java]

        viewModel.allTransactions.observe(this) {
            val last = it.takeLast(5)

            if (last.size > 0) binding.firstTransaction.text = last[0].toString()
            if (last.size > 1) binding.secondTransaction.text = last[1].toString()
            if (last.size > 2) binding.thirdTransaction.text = last[2].toString()
            if (last.size > 3) binding.fourthTransaction.text = last[3].toString()
            if (last.size > 4) binding.fifthTransaction.text = last[4].toString()
        }

        viewModel.allAccounts.observe(this) {
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val activeAccountId = sharedPreferences.getString(getString(R.string.active_account_id), "") ?: ""

            if (activeAccountId != "") {
                binding.balance.text = it.first { it.accountId == activeAccountId }.balance
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val activeAccountId = sharedPreferences.getString(getString(R.string.active_account_id), "") ?: ""

            if (activeAccountId != "") {
                GlobalScope.launch(Dispatchers.IO) {
                    val transactions = StellarApi.getTransactions(activeAccountId)
                    val balance = StellarApi.getBalance(activeAccountId)

                    viewModel.deleteTransaction()
                    val account = viewModel.allAccounts.value?.first { it.accountId == activeAccountId }?.copy(balance = balance)

                    viewModel.updateAccount(account!!)
                    viewModel.insertTransactions(transactions)

                    withContext(Dispatchers.Main) {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
            else {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        return binding.root
    }
}