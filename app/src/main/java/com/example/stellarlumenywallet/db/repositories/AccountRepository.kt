package com.example.stellarlumenywallet.db.repositories

import androidx.annotation.WorkerThread
import com.example.stellarlumenywallet.db.dao.AccountDao
import com.example.stellarlumenywallet.db.entities.Account
import com.example.stellarlumenywallet.db.entities.AccountWithBalances
import kotlinx.coroutines.flow.Flow

@Suppress("RedundantSuspendModifier")
class AccountRepository(private val accountDao: AccountDao) {
    val allAccounts: Flow<List<Account>> = accountDao.getAlphabetizedAccounts()
    val allAccountsWithBalances: Flow<List<AccountWithBalances>> = accountDao.getAccountsWithBalances()

    @WorkerThread
    suspend fun update(account: Account) = accountDao.update(account)

    @WorkerThread
    suspend fun insert(account: Account) = accountDao.insert(account)

    @WorkerThread
    suspend fun delete(account: Account) = accountDao.delete(account)

    @WorkerThread
    suspend fun deleteAll() = accountDao.deleteAll()
}