package com.example.stellarlumenywallet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stellarlumenywallet.db.dao.AccountDao
import com.example.stellarlumenywallet.db.dao.ActiveAccountDao
import com.example.stellarlumenywallet.db.dao.ContactDao
import com.example.stellarlumenywallet.db.dao.TransactionDao
import com.example.stellarlumenywallet.db.entities.*

@Database(entities = [Account::class, Balance::class, Contact::class, Transaction::class, ActiveAccount::class], version = 1, exportSchema = false)
abstract class WalletRoomDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun contactDao(): ContactDao
    abstract fun transactionDao(): TransactionDao
    abstract fun activeAccount(): ActiveAccountDao

    companion object {
        @Volatile
        private var INSTANCE: WalletRoomDatabase? = null

        fun getDatabase(context: Context): WalletRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletRoomDatabase::class.java,
                    "wallet_room_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}