package com.example.stellarlumenywallet.fragments.accountmanagement

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stellarlumenywallet.R
import com.example.stellarlumenywallet.api.StellarApi
import com.example.stellarlumenywallet.databinding.FragmentAccountManagementBinding
import com.example.stellarlumenywallet.db.WalletRoomDatabase
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AccountManagementFragment: Fragment() {
    private lateinit var binding: FragmentAccountManagementBinding
    private lateinit var viewModel: AccountManagementViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_management, container, false)

        val database by lazy { WalletRoomDatabase.getDatabase(requireContext()) }
        val accountRepository by lazy { AccountRepository(database.accountDao()) }
        val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
        val viewModelFactory = AccountManagementViewModelFactory(accountRepository, transactionRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AccountManagementViewModel::class.java]

        val adapter = ArrayAdapter(requireContext(), R.layout.item_account, emptyList<String>())
        binding.autoCompleteView.setAdapter(adapter)

        binding.autoCompleteView.setOnItemClickListener { adapterView, view, i, l ->
            runBlocking(Dispatchers.IO) { launch {
                    val account = viewModel.allAccounts.value?.get(i)
                    if (account != null) {
                        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString(getString(R.string.active_account_id), account.accountId)
                            apply()
                        }

                        viewModel.deleteAllTransactions()
                        val transactions = StellarApi.getTransactions(account.accountId)
                        viewModel.insertTransactions(transactions)
                    }
                }
            }
        }

        binding.buttonCreateNewAccount.setOnClickListener { it.findNavController().navigate(R.id.action_accountManagementFragment_to_registerFragment) }
        binding.buttonAddExistingAccount.setOnClickListener { it.findNavController().navigate(R.id.action_accountManagementFragment_to_addExistingAccountFragment) }

        viewModel.allAccounts.observe(this) { accounts ->
            binding.autoCompleteView.setAdapter(ArrayAdapter(requireContext(), R.layout.item_account, accounts.map { it.publicKey }))
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}