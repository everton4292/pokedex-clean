package com.example.pokedex.plugin.feature.landing.gateway

import br.com.clean.core.gateway.mvvm.BaseViewModel
import com.example.pokedex.landing.business.LandingUseCase
import com.example.pokedex.landing.gateway.LandingController
import com.example.pokedex.landing.gateway.LandingGatewayInjector.Companion.self as injector

class LandingViewModel : BaseViewModel(), LandingController {
    private val fetcher by lazy { injectLanding() }

    protected fun injectLanding(): LandingUseCase {
        return injector.doFetch
    }

    override fun doFetch(channelName: String) {
        dispatchUseCase(null, fetcher) { postValue(channelName, it) }
    }
}