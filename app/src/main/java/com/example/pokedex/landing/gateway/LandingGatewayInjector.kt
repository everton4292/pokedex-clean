package com.example.pokedex.landing.gateway

import com.example.pokedex.landing.business.LandingUseCase

interface LandingGatewayInjector {
    companion object {
        lateinit var self: LandingGatewayInjector
    }

    val doFetch: LandingUseCase
}