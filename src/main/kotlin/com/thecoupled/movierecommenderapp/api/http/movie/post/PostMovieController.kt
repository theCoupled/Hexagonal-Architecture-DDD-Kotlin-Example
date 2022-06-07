package com.thecoupled.movierecommenderapp.api.http.movie.post

import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieCommand
import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostMovieController(
    private val createMovieHandler: CreateMovieHandler
) {

    @PostMapping("/api/v1/movies", produces = ["application/json"])
    fun postMovie(@RequestBody requestBody: PostMovieRequestBody): String {
        return createMovieHandler.handle(requestBody.toCommand()).value.toString()
    }

    data class PostMovieRequestBody(
        val name: String,
        val genreIds: List<String>,
        val themeIds: List<String>,
        val directorIds: List<String>,
        val actorIds: List<String>,
        val countryId: String
    )

    private fun PostMovieRequestBody.toCommand(): CreateMovieCommand =
        CreateMovieCommand(
            name = this.name,
            genreNames = this.genreIds.toSet(),
            themeNames = this.themeIds.toSet(),
            actorNames = this.actorIds.toSet(),
            directorNames = this.directorIds.toSet(),
            countryName = this.countryId
        )
}