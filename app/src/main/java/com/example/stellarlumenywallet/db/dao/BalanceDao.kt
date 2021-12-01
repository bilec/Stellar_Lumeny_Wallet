package com.example.stellarlumenywallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stellarlumenywallet.db.entities.Balance

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(balance: Balance)

    @Query("DELETE from balances")
    fun deleteAll()
}