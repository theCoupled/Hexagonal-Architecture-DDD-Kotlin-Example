package com.thecoupled.movierecommenderapp.api.http.movie.get

import com.thecoupled.movierecommenderapp.api.http.shared.HttpResponseEntity
import com.thecoupled.movierecommenderapp.application.movie.find.FindMovieQueryHandler
import com.thecoupled.movierecommenderapp.application.movie.find.MovieResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class GetMovieController(
    private val findMovieQueryHandler: FindMovieQueryHandler
) {
/*
    @GetMapping("/api/v1/movies/{movieId}", produces = ["application/json"])
    fun getMovie(
        @PathVariable movieId: String
    ): ResponseEntity<HttpResponseEntity> =
        findMovieQueryHandler.handle(FindMovieQuery(id = movieId))
            .toEntityResponse()
*/

    private fun MovieResponse.toEntityResponse(): ResponseEntity<HttpResponseEntity> =
        ResponseEntity.ok(
            HttpResponseEntity(
                data = HttpMoviePayload(
                    id = this.id,
                    name = this.name
                )
            )
        )
}