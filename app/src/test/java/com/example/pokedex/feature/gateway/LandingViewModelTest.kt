package com.example.pokedex.feature.gateway

import com.example.pokedex.mvvm.BaseViewModelTest
import com.example.pokedex.DataFactory
import com.example.pokedex.landing.business.LandingRepository
import com.example.pokedex.landing.business.LandingUseCase
import com.example.pokedex.landing.domain.Landing
import com.example.pokedex.landing.gateway.LandingGatewayInjector
import com.example.pokedex.plugin.feature.landing.gateway.LandingViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LandingViewModelTest : BaseViewModelTest<LandingViewModel>() {
    private lateinit var repository: LandingRepository

    @Before
    override fun setup() {
        super.setup()
    }

    @After
    override fun teardown() {
        super.teardown()
    }

    @Test
    fun `when fetch succeeds, assert callbacks`() {
        val result = arrangeFetch()
        doGet()
        assertViewStateSuccess(result)
    }

    @Test
    fun `when fetch throws exception, assert callbacks`() {
        val exception = RuntimeException()
        whenever(repository.doFetch()).thenThrow(exception)
        doGet()
        assertViewStateError(exception)
    }

    override fun setupViewModel() {
        repository = mock()
        LandingGatewayInjector.self = object : LandingGatewayInjector {
            override val doFetch: LandingUseCase
                get() = LandingUseCase(repository)
        }
        viewModel = spy(LandingViewModel())
    }

    private fun doGet() {
        runBlocking { viewModel?.doFetch(DataFactory.randomUuid()) }
    }


    private fun arrangeFetch(): Landing {
        val result = mock<Landing>()
        whenever(repository.doFetch()).thenReturn(result)

        return result
    }
}