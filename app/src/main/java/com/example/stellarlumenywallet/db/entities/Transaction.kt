package com.example.stellarlumenywallet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.stellar.sdk.Transaction
import java.sql.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "starting_balance") val startingBalance: String? = null,
    @ColumnInfo(name = "transaction_type") val transactionType: String? = null,
    val from: String? = null,
    val amount: String? = null,
    @ColumnInfo(name = "asset_type") val assetType: String? = null



) {
    override fun toString(): String {
        return if (from == null) {
            String.format("+ \t 10000.0000000")
        } else {
            String.format("%s \t %s", transactionType, amount)
        }
    }
}