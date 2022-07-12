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
    val countryIds: CountryId
)

data class MovieCreatorDataSample(
    val name: MovieName,
    val countryId: CountryId,
    val genreIds: Set<GenreId>,
    val themeIds: Set<ThemeId>,
    val directorIds: Set<DirectorId>,
    val actorIds: Set<ActorId>
)