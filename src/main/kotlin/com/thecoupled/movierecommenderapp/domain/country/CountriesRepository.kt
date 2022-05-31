package com.thecoupled.movierecommenderapp.domain.country

interface CountriesRepository {
    fun save(country: Country)
    fun findAll(): List<Country>
    fun query(query: CountriesQuery): List<Country>
    fun nextId(): CountryId
}