package com.thecoupled.movierecommenderapp.application.movie.create

import arrow.core.Invalid
import arrow.core.NonEmptyList
import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.validNel
import arrow.core.zip
import com.thecoupled.movierecommenderapp.domain.actor.ActorsRepository
import com.thecoupled.movierecommenderapp.domain.country.CountriesRepository
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.director.DirectorNotFoundError
import com.thecoupled.movierecommenderapp.domain.director.DirectorsQuery
import com.thecoupled.movierecommenderapp.domain.director.DirectorsRepository
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.genre.GenreNotFoundError
import com.thecoupled.movierecommenderapp.domain.genre.GenresQuery
import com.thecoupled.movierecommenderapp.domain.genre.GenresRepository
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.errors.MovieValidationError
import com.thecoupled.movierecommenderapp.domain.shared.error.DomainErrors
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import com.thecoupled.movierecommenderapp.domain.theme.ThemeNotFoundError
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
    fun create(movieCreatorData: MovieCreatorDataSample): Validated<DomainErrors, String> {

        return movieCreatorData.directorIds.assertNotEmpty().zip(
            movieCreatorData.genreIds.assertNotEmpty(),
            movieCreatorData.themeIds.assertNotEmpty()
        ) { directorIds, genreIds, themeIds ->
            return directorIds.assertExisting().zip(
                themeIds.assertExisting(),
                genreIds.assertExisting()
            ) { directorIds, genreIds, themeIds ->
                "generated id"
            }
        }
    }

    private fun Set<DirectorId>.assertNotEmpty(): Validated<DomainErrors, Set<DirectorId>> =
        when (isEmpty()) {
            true -> MovieValidationError.MissingDirectorIdError.invalidNel()
            else -> this.validNel()
        }

    @JvmName("assertNotEmptyGenreId")
    private fun Set<GenreId>.assertNotEmpty(): Validated<DomainErrors, Set<GenreId>> =
        when (isEmpty()) {
            true -> MovieValidationError.MissingGenreIdError.invalidNel()
            else -> this.validNel()
        }

    @JvmName("assertNotEmptyThemeId")
    private fun Set<ThemeId>.assertNotEmpty(): Validated<DomainErrors, Set<ThemeId>> =
        when (isEmpty()) {
            true -> MovieValidationError.MissingThemeIdError.invalidNel()
            else -> this.validNel()
        }

    private fun Set<DirectorId>.assertExisting(): Validated<DomainErrors, Set<DirectorId>> {
        val existingDirectorIds = directorsRepository.query(DirectorsQuery(ids = this)).map { it.id }
        val missingDirectoryIds = this.filterNot { existingDirectorIds.contains(it) }

        if (missingDirectoryIds.isNotEmpty()) {
            val errors = missingDirectoryIds.map { DirectorNotFoundError(it.toString()) }
            return Invalid(NonEmptyList.fromListUnsafe(errors))
        }

        return this.validNel()
    }

    @JvmName("assertExistingThemeId")
    private fun Set<ThemeId>.assertExisting(): Validated<DomainErrors, Set<ThemeId>> {
        val existingThemeIds = themesRepository.query(ThemesQuery(ids = this)).map { it.id }
        val missingThemes = this.filterNot { existingThemeIds.contains(it) }

        if (missingThemes.isNotEmpty()) {
            val errors = missingThemes.map { ThemeNotFoundError(it.toString()) }
            return Invalid(NonEmptyList.fromListUnsafe(errors))
        }

        return this.validNel()
    }

    @JvmName("assertExistingGenreId")
    private fun Set<GenreId>.assertExisting(): Validated<DomainErrors, Set<GenreId>> {
        val existingGenreIds = genresRepository.query(GenresQuery(ids = this)).map { it.id }
        val missingGenreIds = this.filterNot { existingGenreIds.contains(it) }

        if (missingGenreIds.isNotEmpty()) {
            val errors = missingGenreIds.map { GenreNotFoundError(it.toString()) }
            return Invalid(NonEmptyList.fromListUnsafe(errors))
        }

        return this.validNel()
    }
}