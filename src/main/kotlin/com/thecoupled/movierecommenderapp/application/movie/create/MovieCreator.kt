package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.domain.actor.Actor
import com.thecoupled.movierecommenderapp.domain.actor.ActorsQuery
import com.thecoupled.movierecommenderapp.domain.actor.ActorsRepository
import com.thecoupled.movierecommenderapp.domain.country.CountriesQuery
import com.thecoupled.movierecommenderapp.domain.country.CountriesRepository
import com.thecoupled.movierecommenderapp.domain.country.Country
import com.thecoupled.movierecommenderapp.domain.director.Director
import com.thecoupled.movierecommenderapp.domain.director.DirectorsQuery
import com.thecoupled.movierecommenderapp.domain.director.DirectorsRepository
import com.thecoupled.movierecommenderapp.domain.genre.Genre
import com.thecoupled.movierecommenderapp.domain.genre.GenresQuery
import com.thecoupled.movierecommenderapp.domain.genre.GenresRepository
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieAlreadyExistingException
import com.thecoupled.movierecommenderapp.domain.movie.MoviesQuery
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.createNewMovie
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.theme.Theme
import com.thecoupled.movierecommenderapp.domain.theme.ThemesQuery
import com.thecoupled.movierecommenderapp.domain.theme.ThemesRepository
import java.time.Clock

class MovieCreator(
    private val moviesRepository: MoviesRepository,
    private val countriesRepository: CountriesRepository,
    private val actorsRepository: ActorsRepository,
    private val themesRepository: ThemesRepository,
    private val genresRepository: GenresRepository,
    private val directorsRepository: DirectorsRepository,
    private val clock: Clock
) {
    fun create(movieCreatorData: MovieCreatorData): Pair<Movie, List<DomainEvent>> {
        movieCreatorData.assertMovieNotExisting()
        val createdMovie = movieCreatorData.createNewMovie()
        moviesRepository.save(createdMovie)

        return Pair(createdMovie, listOf())
    }

    private fun MovieCreatorData.assertMovieNotExisting() {
        if (moviesRepository.query(MoviesQuery(andNames = setOf(this.name))).isNotEmpty()) {
            throw MovieAlreadyExistingException()
        }
    }

    private fun MovieCreatorData.createNewMovie(): Movie =
        createNewMovie(
            moviesRepository = moviesRepository,
            name = this.name,
            genres = this.findOrFailGenres(),
            actors = this.findActors(),
            country = this.getOrCreateCountry(),
            themes = this.getOrCreateThemes(),
            directors = this.getOrCreateDirectors(),
            clock = clock
        )

    private fun MovieCreatorData.getOrCreateCountry(): Country {
        val country = countriesRepository.query(CountriesQuery(names = setOf(this.countryName)))
            .firstOrNull() ?: Country(
            id = countriesRepository.nextId(),
            name = this.countryName
        )

        countriesRepository.save(country)

        return country
    }


    private fun MovieCreatorData.getOrCreateThemes(): Set<Theme> {
        val existingThemes = themesRepository.query(ThemesQuery(names = this.themeNames))
        val createdThemes = this.themeNames
            .filterNot { themeName -> existingThemes.any { existingTheme -> existingTheme.name == themeName } }
            .map { themeName -> Theme(id = themesRepository.nextId(), name = themeName) }

        createdThemes.forEach(themesRepository::save)

        return (existingThemes + createdThemes).toSet()
    }

    private fun MovieCreatorData.findActors(): List<Actor> =
        actorsRepository.query(ActorsQuery(ids = this.actorIds))

    private fun MovieCreatorData.findOrFailGenres(): List<Genre> {
        val existingGenres = genresRepository.query(GenresQuery(ids = this.genreIds))
        if (existingGenres.size != this.genreIds.size) {
            throw Exception()
        }

        return existingGenres
    }

    private fun MovieCreatorData.getOrCreateDirectors(): Set<Director> {
        val existingDirectors = directorsRepository.query(DirectorsQuery(names = this.directorNames))
        val createdDirectors = this.directorNames
            .filterNot { directorName -> existingDirectors.any { existingDirector -> existingDirector.name == directorName } }
            .map { directorName -> Director(id = directorsRepository.nextId(), name = directorName) }

        createdDirectors.forEach(directorsRepository::save)

        return (existingDirectors + createdDirectors).toSet()
    }
}