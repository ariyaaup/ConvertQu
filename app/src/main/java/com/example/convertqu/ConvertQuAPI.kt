package com.example.convertqu

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConvertQuAPI {
    val BASE_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_mBhRNHJHIQFF341ecOthwFaID1RzUpqqkNWKmgx8"
    val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val currencyApi : ConvertQuService by lazy {
        retrofit.create(ConvertQuService::class.java      )
    }
}