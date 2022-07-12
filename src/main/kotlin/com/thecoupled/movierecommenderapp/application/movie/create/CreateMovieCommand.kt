package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.Command

data class CreateMovieCommand(
    val name: String,
    val genreIds: Set<String>,
    val actorIds: Set<String>,
    val directorIds: Set<String>,
    val themeIds: Set<String>,
    val countryId: String
) : Command