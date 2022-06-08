package com.thecoupled.movierecommenderapp.application.movie.find

import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import com.thecoupled.movierecommenderapp.domain.movie.MovieNotFoundException
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository

class MovieFinder(private val moviesRepository: MoviesRepository) {
    fun find(id: MovieId): Movie = moviesRepository.find(id) ?: throw MovieNotFoundException()
}