package com.example.pokedex.landing.domain

data class Landing(
    val count: Int,
    val next: String,
    var results: MutableList<Pokemon>)