package com.thecoupled.movierecommenderapp.domain.country

import java.util.*

fun arbitraryCountry(
    id: CountryId = CountryId(UUID.randomUUID()),
    name: CountryName = CountryName("random name")
): Country =
    Country(
        id = id,
        name = name
    )