package com.example.stellarlumenywallet.fragments.register

import androidx.lifecycle.ViewModel
import com.example.stellarlumenywallet.db.entities.Account
import com.example.stellarlumenywallet.db.repositories.AccountRepository

class RegisterViewModel(private val repository: AccountRepository): ViewModel() {
    suspend fun insertAccount(account: Account) = repository.insert(account)
}