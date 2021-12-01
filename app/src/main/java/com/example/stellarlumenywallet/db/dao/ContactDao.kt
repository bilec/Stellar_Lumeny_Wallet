package com.example.stellarlumenywallet.db.dao

import androidx.room.*
import com.example.stellarlumenywallet.db.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAlphabetizedContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM contacts")
    fun deleteAll()
}