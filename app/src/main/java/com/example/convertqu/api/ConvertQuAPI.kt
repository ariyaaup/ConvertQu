package com.example.convertqu.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConvertQuAPI {
    val BASE_URL = "https://api.freecurrencyapi.com"
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val currencyApi : ConvertQuService by lazy {
        retrofit.create(ConvertQuService::class.java      )
    }
}

