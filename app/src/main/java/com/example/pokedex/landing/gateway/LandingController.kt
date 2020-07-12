package com.example.pokedex.landing.gateway

import br.com.clean.core.gateway.mvvm.Controller

interface LandingController : Controller {
    fun doFetch(channelName: String)
}