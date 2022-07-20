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
            directors = this.findOrFailDirectors(),
            themes = this.findOrFailThemes(),
            country = this.findOrFailCountry(),
            clock = clock
        )

    private fun MovieCreatorData.findOrFailCountry(): Country =
        countriesRepository.query(CountriesQuery(ids = setOf(this.countryId)))
            .firstOrNull() ?: throw Exception()


    private fun MovieCreatorData.findOrFailThemes(): List<Theme> {
        val existingThemes = themesRepository.query(ThemesQuery(ids = this.themeIds))
        if (existingThemes.size != this.themeIds.size) {
            throw Exception()
        }

        return existingThemes
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

    private fun MovieCreatorData.findOrFailDirectors(): List<Director> {
        val existingDirectors = directorsRepository.query(DirectorsQuery(ids = this.directorIds))

        if (existingDirectors.size != this.directorIds.size) {
            throw Exception()
        }
        return existingDirectors
    }
}