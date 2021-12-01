package com.example.stellarlumenywallet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class Balance (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "asset_type") val assetType: String,
    val balance: String,
    @ColumnInfo(name = "account_id") val accountID: String
)