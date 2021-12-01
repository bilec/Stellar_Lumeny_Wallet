package com.example.stellarlumenywallet.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class AccountWithBalances (
    @Embedded val account: Account,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "account_id"
    )
    val balances: List<Balance>
)