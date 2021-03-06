package com.example.stellarlumenywallet.fragments.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.entities.Account
import com.example.stellarlumenywallet.db.entities.Transaction
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository

class WalletViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
): ViewModel() {
    val allTransactions = transactionRepository.allTransactions.asLiveData()
    val allAccounts = accountRepository.allAccounts.asLiveData()

    suspend fun insertTransactions(transactions: List<Transaction>) = transactions.forEach { transactionRepository.insert(it) }
    suspend fun updateAccount(account: Account) = accountRepository.update(account)
    suspend fun deleteTransaction() = transactionRepository.deleteAll()
}