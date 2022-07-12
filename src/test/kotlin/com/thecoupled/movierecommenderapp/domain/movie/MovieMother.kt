package com.thecoupled.movierecommenderapp.domain.movie

import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.traverse
import arrow.core.zip
import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import java.time.Instant
import java.util.*

fun arbitraryMovie(
    movieId: String = UUID.randomUUID().toString(),
    movieName: String = "arbitrary movie name",
    actorIds : List<String> = listOf(UUID.randomUUID().toString()),
    genreIds: List<String> = listOf(UUID.randomUUID().toString()),
    themeIds: List<String> = listOf(UUID.randomUUID().toString()),
    directorIds: List<String> = listOf(UUID.randomUUID().toString()),
    countryId: String = UUID.randomUUID().toString(),
    createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
): Movie {
    val movie = MovieId.createFromString(movieId).zip(
        MovieName(movieName),
        actorIds.traverse { ActorId.createFromString(it) },
        genreIds.traverse { GenreId.createFromString(it) },
        themeIds.traverse { ThemeId.createFromString(it) },
        directorIds.traverse { DirectorId.createFromString(it) },
        CountryId.createFromString(countryId)
    ) { movieId, movieName, actorIds, genreIds, themeIds, directorIds, countryId ->
        Movie(
            id = movieId,
            name = movieName,
            genreIds = genreIds.toSet(),
            actorIds = actorIds.toSet(),
            themeIds = themeIds.toSet(),
            directorIds = directorIds.toSet(),
            countryId = countryId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    return when(movie) {
        is Valid -> movie.value
        is Invalid -> throw Exception("Invalid model")
    }
}


/*
fun arbitraryEnrichedMovie(
    id: MovieId = MovieId(UUID.randomUUID()),
    name: MovieName = MovieName("arbitrary movie name"),
    genreIds: Set<GenreId> = setOf(GenreId(UUID.randomUUID())),
    actorIds: Set<ActorId> = setOf(ActorId(UUID.randomUUID())),
    directorsIds: Set<DirectorId> = setOf(DirectorId(UUID.randomUUID())),
    themeIds: Set<ThemeId> = setOf(ThemeId(UUID.randomUUID())),
    countryId: CountryId = CountryId(UUID.randomUUID()),
    createdAt: Instant = Instant.now()
): Movie =
    Movie(
        id = id,
        name = name,
        genreIds = genreIds,
        actorIds = actorIds,
        directorIds = directorsIds,
        themeIds = themeIds,
        countryId = countryId,
        createdAt = createdAt
    )

fun arbitraryEmptyMovie(
    id: MovieId = MovieId(UUID.randomUUID()),
    name: MovieName = MovieName("arbitrary movie name"),
    genreIds: Set<GenreId>,
    actorIds: Set<ActorId>,
    directorsIds: Set<DirectorId>,
    themeIds: Set<ThemeId>,
    countryId: CountryId = CountryId(UUID.randomUUID()),
    createdAt: Instant = Instant.now()
): Movie =
    Movie(
        id = id,
        name = name,
        genreIds = genreIds,
        actorIds = actorIds,
        directorIds = directorsIds,
        themeIds = themeIds,
        countryId = countryId,
        createdAt = createdAt
    )

 */