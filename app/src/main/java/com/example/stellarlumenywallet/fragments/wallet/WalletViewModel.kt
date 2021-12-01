package com.example.stellarlumenywallet.fragments.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository

class WalletViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val activeAccountRepository: ActiveAccountRepository
): ViewModel() {
    val allTransactions = transactionRepository.allTransactions.asLiveData()
    val allAccountsWithBalances = accountRepository.allAccountsWithBalances.asLiveData()
    val activeAccount = activeAccountRepository.activeAccount.asLiveData()
}