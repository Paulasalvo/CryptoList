package com.desafio.cryptolist.model.api

import com.desafio.cryptolist.model.dto.CryptoDTO
import com.desafio.cryptolist.model.dto.CryptoDetailDTO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinCapAPI {
    @GET("assets")
    suspend fun getCryptoCoin():CryptoDTO

    @GET("assets/{id}")
    suspend fun getCoinDetail(@Path("id") id:String):CryptoDetailDTO
}

private fun getCoinCapClient(): Retrofit{
    return Retrofit.Builder()
        .client(OkHttpClient())
        .baseUrl("https://api.coincap.io/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
fun getCoinCapService():CoinCapAPI{
    return getCoinCapClient().create(CoinCapAPI::class.java)
}

