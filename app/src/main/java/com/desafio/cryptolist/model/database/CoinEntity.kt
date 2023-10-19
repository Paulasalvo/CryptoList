package com.desafio.cryptolist.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "coin_table")
data class CoinEntity (
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val supply: String = "",
    val marketCapUsd: String = "",
    val timestamp: Long
    )

