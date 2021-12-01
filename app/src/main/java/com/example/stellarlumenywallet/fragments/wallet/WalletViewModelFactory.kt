package com.example.stellarlumenywallet.fragments.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository

class WalletViewModelFactory(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val activeAccountRepository: ActiveAccountRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(transactionRepository, accountRepository, activeAccountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}