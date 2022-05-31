package com.thecoupled.movierecommenderapp.domain.movie

interface MoviesRepository {
    fun save(movie: Movie)
    fun find(id: MovieId): Movie?
    fun findAll(): List<Movie>
    fun query(query: MovieQuery): List<Movie>
    fun nextId(): MovieId
}