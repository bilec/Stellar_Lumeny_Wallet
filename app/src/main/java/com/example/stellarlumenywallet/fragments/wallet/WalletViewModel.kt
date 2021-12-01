package com.example.stellarlumenywallet.fragments.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.entities.Balance
import com.example.stellarlumenywallet.db.entities.Transaction
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.BalanceRepository
import com.example.stellarlumenywallet.db.repositories.TransactionRepository

class WalletViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val balanceRepository: BalanceRepository
): ViewModel() {
    val allTransactions = transactionRepository.allTransactions.asLiveData()
    val allAccountsWithBalances = accountRepository.allAccountsWithBalances.asLiveData()

    suspend fun insertBalances(balances: List<Balance>) = balances.forEach { balanceRepository.insert(it) }
    suspend fun insertTransactions(transactions: List<Transaction>) = transactions.forEach { transactionRepository.insert(it) }

    suspend fun deleteBalances() = balanceRepository.deleteAll()
    suspend fun deleteTransaction() = transactionRepository.deleteAll()
}