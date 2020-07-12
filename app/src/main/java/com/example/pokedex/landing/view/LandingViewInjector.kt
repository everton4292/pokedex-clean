package com.example.pokedex.landing.view

import br.com.clean.core.gateway.mvvm.ControllerFactory
import com.example.pokedex.landing.gateway.LandingController

interface LandingViewInjector {
    companion object {
        lateinit var self: LandingViewInjector
    }

    val controllerFactory: ControllerFactory<LandingFragment, LandingController>
}