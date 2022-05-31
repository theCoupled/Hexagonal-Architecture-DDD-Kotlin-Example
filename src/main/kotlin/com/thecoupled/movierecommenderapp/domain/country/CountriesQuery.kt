package com.thecoupled.movierecommenderapp.domain.country

data class CountriesQuery(
    val names: Set<CountryName>? = null
)