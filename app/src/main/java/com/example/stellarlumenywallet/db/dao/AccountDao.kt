package com.example.stellarlumenywallet.db.dao

import androidx.room.*
import com.example.stellarlumenywallet.db.entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts ORDER BY account_id ASC")
    fun getAlphabetizedAccounts(): Flow<List<Account>>

    @Update
    fun update(account: Account)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(account: Account)

    @Delete
    fun delete(account: Account)

    @Query("DELETE FROM accounts")
    fun deleteAll()
}