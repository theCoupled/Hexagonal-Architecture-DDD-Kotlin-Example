package com.thecoupled.movierecommenderapp.application.movie.find

import org.springframework.stereotype.Service

@Service
class FindMovieQueryHandler(private val movieFinder: MovieFinder)
