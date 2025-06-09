package com.example.convertqu.api

import com.example.convertqu.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ConvertQuService {
    @GET("v1/latest?apikey=fca_live_mBhRNHJHIQFF341ecOthwFaID1RzUpqqkNWKmgx8")
    fun getExchangeRates(): Call<ApiResponse>
}
