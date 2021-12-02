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
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val activeAccountId = sharedPreferences.getString(getString(R.string.active_account_id), "") ?: ""

            GlobalScope.launch(Dispatchers.IO) {
                if (activeAccountId == "") return@launch

                val balances = viewModel.allAccountsWithBalances.value?.first { it.account.accountId == activeAccountId }?.balances
                if (balances != null) {
                    withContext(Dispatchers.Main) {
                    }
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.IO) {
                val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val activeAccountId = sharedPreferences.getString(getString(R.string.active_account_id), "") ?: ""

                if (activeAccountId == "") {
                    withContext(Dispatchers.Main) { binding.swipeRefreshLayout.isRefreshing = false }
                    return@launch
                }

                val balances = StellarApi.getBalances("GBW6TMLL3QMR4CDPW6RVVHPBLNUYWKMLBRKQEQU2CHWXAE7CFGFDKMBE")
                val transactions = StellarApi.getTransactions("GBW6TMLL3QMR4CDPW6RVVHPBLNUYWKMLBRKQEQU2CHWXAE7CFGFDKMBE")

                viewModel.deleteTransaction()

                viewModel.insertTransactions(transactions)

                withContext(Dispatchers.Main) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        return binding.root
    }
}