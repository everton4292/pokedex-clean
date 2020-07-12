package com.example.pokedex.plugin.api

import com.google.gson.Gson
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokedexAPIBuilder(baseUrl: String?, interceptors: List<Interceptor> = emptyList()) :
    AbstractBuilder<PokedexAPI>(baseUrl, interceptors) {

    override fun build(): PokedexAPI {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(PokedexAPI::class.java)
    }
}