package com.thecoupled.movierecommenderapp.application.movie.find

import com.thecoupled.movierecommenderapp.application.shared.bus.query.Response

data class MovieResponse(
    val id: String,
    val name: String
) : Response