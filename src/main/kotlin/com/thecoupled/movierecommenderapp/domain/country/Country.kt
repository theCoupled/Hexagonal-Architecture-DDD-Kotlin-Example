package com.thecoupled.movierecommenderapp.domain.country

import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class Country(
    override val id: CountryId,
    val name: CountryName
) : Aggregate