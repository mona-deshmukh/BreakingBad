package com.example.breakingbad

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val baseUrl = "https://www.breakingbadapi.com/api/"

    fun getBreakingBadAPI(): BreakingBadApi {
        val gson: Gson = GsonBuilder().setLenient()
            .create()
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(BreakingBadApi::class.java)
    }
}