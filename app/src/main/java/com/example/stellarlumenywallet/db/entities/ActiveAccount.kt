package com.example.stellarlumenywallet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_account")
data class ActiveAccount(
    @PrimaryKey @ColumnInfo(name = "account_id") val accountId: String
)