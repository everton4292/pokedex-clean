package com.example.pokedex.repository

import br.com.clean.core.business.exception.HttpException
import com.example.pokedex.DataFactory
import com.example.pokedex.plugin.feature.landing.LandingRepositoryImpl
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Test

class LandingRepositoryImplTest : InRepositoryTest<LandingRepositoryImpl>() {
    override fun setup() {
        super.setup()
    }

    override fun setupRepository(httpUrl: String) {
        repository = spy(LandingRepositoryImpl(httpUrl))
    }

    @Test
    fun `when fetch succeeds, the return a Result`() {
        val value = DataFactory.mockLanding()
        doReturn(value).`when`(repository).doFetch()

        val result = repository.doFetch()
        Assert.assertEquals(value, result)
    }

    @Test(expected = HttpException::class)
    fun `when fetch request returns 5XX, then throw HttpException `() {
        enqueueEmptyResponse(500)

        repository.doFetch()
        val request = server.takeRequest()
        Assert.assertNotNull(request.body)
    }

}