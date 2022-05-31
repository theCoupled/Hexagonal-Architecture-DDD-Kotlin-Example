package com.thecoupled.movierecommenderapp.domain.director

interface DirectorsRepository {
    fun save(country: Director)
    fun findAll(): List<Director>
    fun query(query: DirectorsQuery): List<Director>
    fun nextId(): DirectorId
}