package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.actor.Actor
import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.Country
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.Director
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.Genre
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.shared.Aggregate
import com.thecoupled.movierecommenderapp.domain.theme.Theme
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import java.time.Clock
import java.time.Instant

data class Movie(
    override val id: MovieId,
    val name: MovieName,
    val genreIds: Set<GenreId>,
    val actorIds: Set<ActorId>,
    val directorIds: Set<DirectorId>,
    val themeIds: Set<ThemeId>,
    val countryId: CountryId,
    val createdAt: Instant
) : Aggregate {
    init {
        if (genreIds.isEmpty()) {
            throw MissingGenreException()
        }

        if (directorIds.isEmpty()) {
            throw MissingDirectorException()
        }

        if (themeIds.isEmpty()) {
            throw MissingThemeException()
        }
    }
}

fun createNewMovie(
    moviesRepository: MoviesRepository,
    name: MovieName,
    genres: List<Genre>,
    actors: List<Actor>,
    directors: Set<Director>,
    themes: Set<Theme>,
    country: Country,
    clock: Clock
): Movie =
    Movie(
        id = moviesRepository.nextId(),
        name = name,
        genreIds = genres.map(Genre::id).toSet(),
        actorIds = actors.map(Actor::id).toSet(),
        directorIds = directors.map(Director::id).toSet(),
        themeIds = themes.map(Theme::id).toSet(),
        countryId = country.id,
        createdAt = Instant.now(clock)
    )