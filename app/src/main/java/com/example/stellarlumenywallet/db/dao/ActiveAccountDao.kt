package com.example.stellarlumenywallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stellarlumenywallet.db.entities.ActiveAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface ActiveAccountDao {

    @Query("SELECT * FROM active_account")
    fun getActiveAccount(): Flow<ActiveAccount>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(activeAccount: ActiveAccount)

    @Query("DELETE FROM active_account")
    fun deleteAll()

}