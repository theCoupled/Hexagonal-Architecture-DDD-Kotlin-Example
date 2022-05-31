package com.thecoupled.movierecommenderapp.domain.genre

interface GenresRepository {
    fun save(theme: Genre)
    fun findAll(): List<Genre>
    fun query(query: GenresQuery): List<Genre>
    fun nextId(): GenreId
}