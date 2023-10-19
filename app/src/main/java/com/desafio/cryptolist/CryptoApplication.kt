package com.desafio.cryptolist

import android.app.Application
import androidx.room.Room
import com.desafio.cryptolist.model.database.CoinDB

class CryptoApplication: Application() {
    companion object {
        var assetsDatabase: CoinDB? = null
    }
    override fun onCreate() {
        super.onCreate()
        assetsDatabase =
            Room.databaseBuilder(this, CoinDB::class.java, "crypto_db").build()
    }


}