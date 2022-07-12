package com.thecoupled.movierecommenderapp.domain.genre


interface GenresRepository {

    fun find(id: GenreId): Genre?
    fun save(theme: Genre)
    fun findAll(): List<Genre>
    fun query(query: GenresQuery): List<Genre>
}