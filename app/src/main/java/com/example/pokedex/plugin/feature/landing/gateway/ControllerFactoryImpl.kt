package com.example.pokedex.plugin.feature.landing.gateway

import androidx.lifecycle.ViewModelProviders
import br.com.clean.core.gateway.mvvm.ControllerFactory
import com.example.pokedex.landing.gateway.LandingController
import com.example.pokedex.landing.view.LandingFragment

class ControllerFactoryImpl : ControllerFactory<LandingFragment, LandingController> {
    override fun create(context: LandingFragment): LandingController {
        return ViewModelProviders.of(context).get(LandingViewModel::class.java)
    }
}