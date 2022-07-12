package com.thecoupled.movierecommenderapp.domain.country

interface CountriesRepository {
    fun find(countryId: CountryId): Country?
    fun save(country: Country)
    fun findAll(): List<Country>
    fun query(query: CountriesQuery): List<Country>

}