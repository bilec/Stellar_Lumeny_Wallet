package com.example.stellarlumenywallet.fragments.accountmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.entities.Transaction
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository

class AccountManagementViewModel(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
): ViewModel() {
    val allAccounts = accountRepository.allAccounts.asLiveData()

    suspend fun insertTransactions(transactions: List<Transaction>) = transactions.forEach { transactionRepository.insert(it) }
    suspend fun deleteAllTransactions() = transactionRepository.deleteAll()
}