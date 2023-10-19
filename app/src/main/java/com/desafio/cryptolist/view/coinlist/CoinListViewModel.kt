package com.desafio.cryptolist.view.coinlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafio.cryptolist.CryptoApplication
import com.desafio.cryptolist.model.api.CoinCapAPI
import com.desafio.cryptolist.model.api.getCoinCapService
import com.desafio.cryptolist.model.database.CoinEntity
import com.desafio.cryptolist.model.dto.CryptoDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinListViewModel: ViewModel() {
    private var apiService: CoinCapAPI = getCoinCapService()
    val coinListLiveData: LiveData<List<CoinEntity>> =
        CryptoApplication.assetsDatabase?.CoinDAO()?.getAssets() ?: MutableLiveData()

    init {
        fetchCoinList()
    }

    private fun fetchCoinList(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cryptoDTO=apiService.getCryptoCoin()
                val database= CryptoApplication.assetsDatabase
                database?.CoinDAO()?.insert(
                    cryptoDTO.data.map {
                        CoinEntity(
                            id = it.id,
                            symbol= it.symbol,
                            name= it.name,
                            priceUsd= it.priceUsd.toString(),
                            supply = it.supply.toString(),
                            marketCapUsd = it.marketCapUsd.toString(),
                            timestamp= cryptoDTO.timestamp
                        )
                    }
                )
            }catch (e:Exception){
                Log.i("API ERROR", e.toString())
            }

        }
    }
}