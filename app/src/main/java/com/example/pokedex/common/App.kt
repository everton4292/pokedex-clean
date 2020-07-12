package com.example.pokedex.common

import android.app.Application
import androidx.fragment.app.Fragment
import br.com.clean.core.gateway.mvvm.ControllerFactory
import com.example.pokedex.landing.business.LandingRepository
import com.example.pokedex.landing.business.LandingUseCase
import com.example.pokedex.landing.gateway.LandingController
import com.example.pokedex.landing.gateway.LandingGatewayInjector
import com.example.pokedex.landing.view.LandingFragment
import com.example.pokedex.landing.view.LandingViewInjector
import com.example.pokedex.plugin.feature.landing.LandingRepositoryImpl
import com.example.pokedex.plugin.feature.landing.gateway.ControllerFactoryImpl

class App : Application() {
    private val baseApiUrl = "https://pokeapi.co/api/v2/"

    override fun onCreate() {
        super.onCreate()

        LandingGatewayInjector.self = object : LandingGatewayInjector {
            override val doFetch: LandingUseCase
                get() = LandingUseCase(injectaCacheRepository())
        }

        LandingViewInjector.self = object : LandingViewInjector {
            override val controllerFactory: ControllerFactory<LandingFragment, LandingController>
                get() = ControllerFactoryImpl()
        }
    }

    private fun injectaCacheRepository(): LandingRepository {
        return LandingRepositoryImpl(baseApiUrl)
    }
}