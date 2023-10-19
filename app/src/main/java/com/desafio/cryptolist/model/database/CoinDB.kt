package com.desafio.cryptolist.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoinEntity::class], version = 1)
abstract class CoinDB : RoomDatabase() {
    abstract fun CoinDAO(): CoinDAO
}