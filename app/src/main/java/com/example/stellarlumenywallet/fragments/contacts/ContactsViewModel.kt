package com.example.stellarlumenywallet.fragments.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stellarlumenywallet.db.entities.Contact
import com.example.stellarlumenywallet.db.repositories.ContactRepository

class ContactsViewModel(private val repository: ContactRepository): ViewModel() {
    val contacts: LiveData<List<Contact>> = repository.allContacts.asLiveData()
}