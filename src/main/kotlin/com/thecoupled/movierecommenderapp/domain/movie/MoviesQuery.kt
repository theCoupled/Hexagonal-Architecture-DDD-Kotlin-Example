package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId

data class MoviesQuery(
    val andIds: Set<MovieId>? = null,
    val andNames : Set<MovieName>? = null,
    val andExcludeIds: Set<MovieId>? = null,

    val orDirectorIds: Set<DirectorId>? = null,
    val orCountryIds: Set<CountryId>? = null,
    val orGenreIds: Set<GenreId>? = null,
    val orActorIds: Set<ActorId>? = null,
    val orThemeIds: Set<ThemeId>? = null,

    val limit: Int? = null,
    val orderByCreatedAtDesc: Boolean = true
)