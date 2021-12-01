package com.example.stellarlumenywallet.db.repositories

import androidx.annotation.WorkerThread
import com.example.stellarlumenywallet.db.dao.ContactDao
import com.example.stellarlumenywallet.db.entities.Contact

@Suppress("RedundantSuspendModifier")
class ContactRepository(private val contactDao: ContactDao) {
    val allContacts = contactDao.getAlphabetizedContacts()

    @WorkerThread
    suspend fun insert(contact: Contact) = contactDao.insert(contact)

    @WorkerThread
    suspend fun delete(contact: Contact) = contactDao.delete(contact)

    @WorkerThread
    suspend fun deleteAll() = contactDao.deleteAll()
}