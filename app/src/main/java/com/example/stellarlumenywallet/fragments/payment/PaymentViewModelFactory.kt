package com.example.stellarlumenywallet.fragments.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ActiveAccountRepository
import com.example.stellarlumenywallet.db.repositories.ContactRepository

class PaymentViewModelFactory(
    private val contactRepository: ContactRepository,
    private val accountRepository: AccountRepository,
    private val activeAccountRepository: ActiveAccountRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(contactRepository, accountRepository, activeAccountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}