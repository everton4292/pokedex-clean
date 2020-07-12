package com.example.pokedex.landing.view

import android.view.View
import androidx.lifecycle.Observer
import br.com.clean.core.business.dto.ValueOutput
import br.com.clean.core.view.ui.BaseFragment
import com.example.pokedex.R
import com.example.pokedex.landing.domain.Landing
import com.example.pokedex.landing.gateway.LandingController
import com.example.pokedex.landing.view.LandingViewInjector.Companion.self as injector

class LandingFragment : BaseFragment<LandingController>() {
    companion object {
        const val landingChannel = "landingChannel"
    }

    override fun channelName(): String = landingChannel

    override fun getLayout(): Int = R.layout.landing_fragment

    override fun setupViews(view: View) {

    }

    override fun onStart() {
        super.onStart()
        fetch()
    }

    override fun handleSuccess(value: Any?) {
        when (value) {
            is Landing? -> loadLanding(value)
        }
    }

    override fun setupController(): LandingController {
        return injector.controllerFactory.create(this)
    }

    private fun fetch() {
        controller.doFetch(landingChannel)
    }

    private fun loadLanding(landing: Landing?) {
        landing?.count
    }
}