package com.example.stellarlumenywallet.fragments.accountmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.entities.ActiveAccount
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository

class AccountManagementViewModel(
    private val accountRepository: AccountRepository,
    private val activeAccountRepository: ActiveAccountRepository
): ViewModel() {
    val allAccounts = accountRepository.allAccounts.asLiveData()

    suspend fun insert(activeAccount: ActiveAccount) = activeAccountRepository.insert(activeAccount)

    suspend fun deleteAllActiveAccount() = activeAccountRepository.deleteAll()
}