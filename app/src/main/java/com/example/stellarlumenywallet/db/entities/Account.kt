package com.example.stellarlumenywallet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey @ColumnInfo(name = "account_id") val accountId: String,
    @ColumnInfo(name = "public_key") val publicKey: String,
    @ColumnInfo(name = "secret_seed") val secretSeed: String
)