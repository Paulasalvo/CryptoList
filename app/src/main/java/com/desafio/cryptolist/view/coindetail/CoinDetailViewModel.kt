package com.desafio.cryptolist.view.coindetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafio.cryptolist.CryptoApplication
import com.desafio.cryptolist.model.api.CoinCapAPI
import com.desafio.cryptolist.model.api.getCoinCapService
import com.desafio.cryptolist.model.database.CoinDAO
import com.desafio.cryptolist.model.database.CoinDB
import com.desafio.cryptolist.model.database.CoinEntity
import com.desafio.cryptolist.model.dto.CoinDTO
import com.desafio.cryptolist.model.dto.CryptoDTO
import com.desafio.cryptolist.model.dto.CryptoDetailDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CoinDetailViewModel: ViewModel() {
    private var apiService: CoinCapAPI = getCoinCapService()

    var coinDetailLiveData: MutableLiveData<CoinEntity?> = MutableLiveData()

    fun getCoinDetail(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val database= CryptoApplication.assetsDatabase
            val cryptoEntity= database?.CoinDAO()?.getAsset(id)

            coinDetailLiveData.postValue(cryptoEntity?.first())
        }
    }


    fun fetchCoinDetail(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cryptoDTO=apiService.getCoinDetail(id)
                val database= CryptoApplication.assetsDatabase
                val coinData = cryptoDTO.data
                coinData?.let {
                    val coinEntity = CoinEntity(
                        id = coinData.id,
                        symbol = coinData.symbol,
                        name = coinData.name,
                        priceUsd = coinData.priceUsd.toString(),
                        supply = coinData.supply.toString(),
                        marketCapUsd = coinData.marketCapUsd.toString(),
                        timestamp = cryptoDTO.timestamp
                    )
                    database?.CoinDAO()?.insert(coinEntity)
                }
            }catch (e:Exception){
                Log.i("API ERROR", e.toString())
            }

        }
    }
}