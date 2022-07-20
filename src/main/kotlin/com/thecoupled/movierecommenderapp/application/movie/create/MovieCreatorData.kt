package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId

data class MovieCreatorData(
    val name: MovieName,
    val genreIds: Set<GenreId>,
    val actorIds: Set<ActorId>,
    val directorIds: Set<DirectorId>,
    val themeIds: Set<ThemeId>,
    val countryId: CountryId
)