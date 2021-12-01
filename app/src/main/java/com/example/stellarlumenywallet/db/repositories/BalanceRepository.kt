package com.example.stellarlumenywallet.db.repositories

import androidx.annotation.WorkerThread
import com.example.stellarlumenywallet.db.dao.BalanceDao
import com.example.stellarlumenywallet.db.entities.Balance

@Suppress("RedundantSuspendModifier")
class BalanceRepository(private val balanceDao: BalanceDao) {

    @WorkerThread
    suspend fun insert(balance: Balance) = balanceDao.insert(balance)

    suspend fun deleteAll() = balanceDao
}