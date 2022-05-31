package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import java.util.*

fun arbitraryMovie(
    id: MovieId = MovieId(UUID.randomUUID()),
    name: MovieName = MovieName("arbitrary movie name"),
    genreIds: Set<GenreId> = setOf(GenreId(UUID.randomUUID())),
    actorIds: Set<ActorId> = setOf(ActorId(UUID.randomUUID())),
    directorsIds: Set<DirectorId> = setOf(DirectorId(UUID.randomUUID())),
    themeIds: Set<ThemeId> = setOf(ThemeId(UUID.randomUUID())),
    countryId: CountryId = CountryId(UUID.randomUUID())
): Movie =
    Movie(
        id = id,
        name = name,
        genreIds = genreIds,
        actorIds = actorIds,
        directorIds = directorsIds,
        themeIds = themeIds,
        countryId = countryId
    )