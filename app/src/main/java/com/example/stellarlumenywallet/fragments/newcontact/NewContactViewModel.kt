package com.example.stellarlumenywallet.fragments.newcontact

import androidx.lifecycle.ViewModel
import com.example.stellarlumenywallet.db.entities.Contact
import com.example.stellarlumenywallet.db.repositories.ContactRepository

class NewContactViewModel(private val repository: ContactRepository): ViewModel() {
    suspend fun insertContact(contact: Contact) = repository.insert(contact)
}