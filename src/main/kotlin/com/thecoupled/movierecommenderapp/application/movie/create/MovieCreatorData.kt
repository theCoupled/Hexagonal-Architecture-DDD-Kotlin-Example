package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName

data class MovieCreatorData(
    val name: MovieName,
    val genreIds: Set<GenreId>,
    val actorIds: Set<ActorId>,
    val directorNames: Set<DirectorName>,
    val themeNames: Set<ThemeName>,
    val countryName: CountryName
)