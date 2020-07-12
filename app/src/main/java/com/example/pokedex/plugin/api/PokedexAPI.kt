package com.example.pokedex.plugin.api

import androidx.annotation.VisibleForTesting
import com.example.pokedex.landing.domain.Landing
import retrofit2.Call
import retrofit2.http.GET

interface PokedexAPI {
    @GET("pokemon")
    fun fetch(): Call<Landing>

    @GET("test")
    @VisibleForTesting
    fun dumbRequest(): Call<Any?>
}