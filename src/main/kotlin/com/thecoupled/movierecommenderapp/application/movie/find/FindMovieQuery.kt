package com.thecoupled.movierecommenderapp.application.movie.find

import com.thecoupled.movierecommenderapp.application.shared.bus.query.Query

data class FindMovieQuery(
    val id : String
) : Query