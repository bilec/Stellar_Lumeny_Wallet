package com.example.stellarlumenywallet.fragments.accountmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository

class AccountManagementViewModelFactory(private val accountRepository: AccountRepository, private val activeAccountRepository: ActiveAccountRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountManagementViewModel::class.java)) {
            return AccountManagementViewModel(accountRepository, activeAccountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}