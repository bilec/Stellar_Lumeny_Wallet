package com.example.stellarlumenywallet.db.repositories

import androidx.annotation.WorkerThread
import com.example.stellarlumenywallet.db.dao.ActiveAccountDao
import com.example.stellarlumenywallet.db.entities.ActiveAccount
import kotlinx.coroutines.flow.Flow

@Suppress("RedundantSuspendModifier")
class ActiveAccountRepository(private val activeAccountDao: ActiveAccountDao) {
    val activeAccount: Flow<ActiveAccount> = activeAccountDao.getActiveAccount()

    @WorkerThread
    suspend fun insert(activeAccount: ActiveAccount) = activeAccountDao.insert(activeAccount)

    @WorkerThread
    suspend fun deleteAll() = activeAccountDao.deleteAll()
}