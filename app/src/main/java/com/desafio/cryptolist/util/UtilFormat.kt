package com.desafio.cryptolist.util

object UtilFormat {
    fun getPriceFormatted(priceUsd:Double):String{
        return "USD$ "+ String.format("%.6f", priceUsd)
    }
    fun getImageURLFormatted(symbol: String) =
        "https://static.coincap.io/assets/icons/${symbol.lowercase()}@2x.png"

}