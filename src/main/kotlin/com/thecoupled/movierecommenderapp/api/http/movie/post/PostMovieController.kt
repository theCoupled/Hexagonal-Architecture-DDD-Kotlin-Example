package com.thecoupled.movierecommenderapp.api.http.movie.post

import com.fasterxml.jackson.annotation.JsonProperty
import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieCommand
import com.thecoupled.movierecommenderapp.application.movie.create.CreateMovieHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class PostMovieController(
    private val createMovieHandler: CreateMovieHandler
) {


    @PostMapping("/api/v1/movies", produces = ["application/json"])
    fun postMovie(@RequestBody requestBody: PostMovieRequestBody): ResponseEntity<Unit> {
        val id = createMovieHandler.handle(requestBody.toCommand()).value.toString()
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri()
        ).build()
    }

    data class PostMovieRequestBody(
        val name: String,
        @JsonProperty("genre_ids")
        val genreIds: List<String>,
        @JsonProperty("theme_ids")
        val themeIds: List<String>,
        @JsonProperty("director_ids")
        val directorIds: List<String>,
        @JsonProperty("actor_ids")
        val actorIds: List<String>,
        @JsonProperty("country_id")
        val countryId: String
    )

    private fun PostMovieRequestBody.toCommand(): CreateMovieCommand =
        CreateMovieCommand(
            name = this.name,
            genreIds = this.genreIds.toSet(),
            themeIds = this.themeIds.toSet(),
            actorIds = this.actorIds.toSet(),
            directorIds = this.directorIds.toSet(),
            countryId = this.countryId
        )
}