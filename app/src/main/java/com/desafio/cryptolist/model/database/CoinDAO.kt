package com.desafio.cryptolist.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CoinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assets: List<CoinEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assets: CoinEntity)
    // TODO pueden deben ser pojos distintos y agregar conversores
    @Query("SELECT * FROM coin_table order by name")
    fun getAssets(): LiveData<List<CoinEntity>>
    @Query("SELECT * FROM coin_table WHERE id=:id")
    fun getAsset(id: String): Flow<CoinEntity>

}