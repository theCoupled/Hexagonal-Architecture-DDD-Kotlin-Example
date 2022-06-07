package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.domain.actor.ActorName
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.genre.GenreName
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName

data class MovieCreatorData(
    val name: MovieName,
    val genreNames: Set<GenreName>,
    val actorNames: Set<ActorName>,
    val directorNames: Set<DirectorName>,
    val themeNames: Set<ThemeName>,
    val countryName: CountryName
)