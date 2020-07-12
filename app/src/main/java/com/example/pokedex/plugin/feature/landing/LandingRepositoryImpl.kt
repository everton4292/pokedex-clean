package com.example.pokedex.plugin.feature.landing

import com.example.pokedex.landing.business.LandingRepository
import com.example.pokedex.landing.domain.Landing
import com.example.pokedex.plugin.api.BaseRepository
import com.example.pokedex.plugin.api.PokedexAPI
import com.example.pokedex.plugin.api.PokedexAPIBuilder
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

open class LandingRepositoryImpl(url: String) :
    BaseRepository(url), LandingRepository {

    override fun doFetch(): Landing? {
        return getBodyOrThrow(getService().fetch())
    }

    override fun getService(interceptors: List<Interceptor>): PokedexAPI {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val list = mutableListOf(loggingInterceptor)
        return PokedexAPIBuilder(baseUrl, list).build()
    }
}