package com.thecoupled.movierecommenderapp.api.http.movie.post

import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieHandler
import com.thecoupled.movierecommenderapp.domain.movie.MovieId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class GetMovieController(
    private val createMovieHandler: CreateMovieHandler
) {

    @GetMapping("/api/v1/movies/{movieId}", produces = ["application/json"])
    fun postMovie(
        @PathVariable movieId: String
    ) {
        val movieIdO = MovieId(UUID.fromString(movieId))
    }

}