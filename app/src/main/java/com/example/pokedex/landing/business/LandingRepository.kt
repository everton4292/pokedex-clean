package com.example.pokedex.landing.business

import com.example.pokedex.landing.domain.Landing

interface LandingRepository {
    fun doFetch(): Landing?
}