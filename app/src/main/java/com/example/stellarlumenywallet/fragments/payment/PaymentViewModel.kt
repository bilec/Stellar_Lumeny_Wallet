package com.example.stellarlumenywallet.fragments.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.entities.Account
import com.example.stellarlumenywallet.db.entities.AccountWithBalances
import com.example.stellarlumenywallet.db.entities.Contact
import com.example.stellarlumenywallet.db.repositories.AccountRepository
import com.example.stellarlumenywallet.db.repositories.ContactRepository

class PaymentViewModel(
    private val contactRepository: ContactRepository,
    private val accountRepository: AccountRepository,
): ViewModel() {
    val contacts: LiveData<List<Contact>> = contactRepository.allContacts.asLiveData()
    val accountsWithBalances: LiveData<List<AccountWithBalances>> = accountRepository.allAccountsWithBalances.asLiveData()

    val accounts: LiveData<List<Account>> = accountRepository.allAccounts.asLiveData()

    lateinit var selectedContactAccountId: String
}