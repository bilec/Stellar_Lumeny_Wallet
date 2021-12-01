package com.example.stellarlumenywallet.db.repositories

import androidx.annotation.WorkerThread
import com.example.stellarlumenywallet.db.dao.TransactionDao
import com.example.stellarlumenywallet.db.entities.Transaction

@Suppress("RedundantSuspendModifier")
class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions = transactionDao.getTransactions()

    @WorkerThread
    suspend fun insert(transaction: Transaction) = transactionDao.insert(transaction)

    @WorkerThread
    suspend fun delete(transaction: Transaction) = transactionDao.delete(transaction)

    @WorkerThread
    suspend fun deleteAll() = transactionDao.deleteAll()
}