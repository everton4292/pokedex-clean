package com.example.pokedex.plugin.api

import androidx.annotation.VisibleForTesting
import br.com.clean.core.business.exception.AuthenticationException
import br.com.clean.core.business.exception.HttpException
import br.com.clean.core.business.exception.InternetConnectionException
import okhttp3.Interceptor
import retrofit2.Call
import java.io.IOException

abstract class BaseRepository(protected val baseUrl: String?) {
    protected fun <R> getBodyOrThrow(call: Call<R>): R? {
        try {
            val response = call.execute()
            if (response.isSuccessful) return response.body()
            if (response.code() == 401) {
                throw AuthenticationException()
            }
            throw HttpException(response.code(), response.message())
        } catch (e: IOException) {
            e.printStackTrace()
            throw InternetConnectionException()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    abstract fun getService(interceptors: List<Interceptor> = emptyList()): PokedexAPI

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun dumbRequest() {
        getBodyOrThrow(getService().dumbRequest())
    }
}