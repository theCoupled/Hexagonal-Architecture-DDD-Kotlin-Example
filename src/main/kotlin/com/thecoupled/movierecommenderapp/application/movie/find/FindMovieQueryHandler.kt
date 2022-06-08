package com.thecoupled.movierecommenderapp.application.movie.find

import com.thecoupled.movierecommenderapp.application.shared.bus.query.QueryHandler
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindMovieQueryHandler(private val movieFinder: MovieFinder) : QueryHandler<FindMovieQuery, MovieResponse> {
    override fun handle(query: FindMovieQuery): MovieResponse =
        movieFinder.find(id = MovieId(UUID.fromString(query.id))).toResponse()

    private fun Movie.toResponse(): MovieResponse =
        MovieResponse(
            id = id.value.toString(),
            name = name.value
        )
}