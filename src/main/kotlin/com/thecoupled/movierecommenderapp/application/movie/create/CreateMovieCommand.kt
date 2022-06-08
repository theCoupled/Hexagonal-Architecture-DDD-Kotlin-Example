package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.Command

data class CreateMovieCommand(
    val name: String,
    val genreNames: Set<String>,
    val actorNames: Set<String>,
    val directorNames: Set<String>,
    val themeNames: Set<String>,
    val countryName: String
) : Command