package com.example.breakingbad

import retrofit2.Call
import retrofit2.http.GET

interface BreakingBadApi {
    @GET("characters")
    fun getCharacters(): Call<ArrayList<Character>>
}