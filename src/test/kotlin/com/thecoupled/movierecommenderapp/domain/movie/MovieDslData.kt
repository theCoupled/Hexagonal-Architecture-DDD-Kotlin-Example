package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.actor.ActorName
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.genre.GenreName
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName

data class MovieDslData(
    val name: MovieName,
    val genreNames: Set<GenreName>,
    val actorNames: Set<ActorName>,
    val directorNames: Set<DirectorName>,
    val themeNames: Set<ThemeName>,
    val countryName: CountryName
)
