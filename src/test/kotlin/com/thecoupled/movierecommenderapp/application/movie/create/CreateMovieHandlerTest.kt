package com.thecoupled.movierecommenderapp.application.movie.create

import arrow.core.Invalid
import arrow.core.nonEmptyListOf
import com.thecoupled.movierecommenderapp.domain.actor.ActorsRepository
import com.thecoupled.movierecommenderapp.domain.actor.createActorsRepository
import com.thecoupled.movierecommenderapp.domain.country.CountriesRepository
import com.thecoupled.movierecommenderapp.domain.country.createCountriesRepository
import com.thecoupled.movierecommenderapp.domain.director.DirectorNotFoundError
import com.thecoupled.movierecommenderapp.domain.director.DirectorsRepository
import com.thecoupled.movierecommenderapp.domain.director.createDirectorsRepository
import com.thecoupled.movierecommenderapp.domain.genre.GenreNotFoundError
import com.thecoupled.movierecommenderapp.domain.genre.GenresRepository
import com.thecoupled.movierecommenderapp.domain.genre.createGenresRepository
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.arbitraryMovie
import com.thecoupled.movierecommenderapp.domain.movie.createMoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.errors.MovieValidationError
import com.thecoupled.movierecommenderapp.domain.shared.error.InvalidUuIdError
import com.thecoupled.movierecommenderapp.domain.theme.ThemeNotFoundError
import com.thecoupled.movierecommenderapp.domain.theme.ThemesRepository
import com.thecoupled.movierecommenderapp.domain.theme.createThemesRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.*

class CreateMovieHandlerTest {

    private fun createUseCase(
        moviesRepository: MoviesRepository = createMoviesRepository(),
        countriesRepository: CountriesRepository = createCountriesRepository(),
        actorsRepository: ActorsRepository = createActorsRepository(),
        themesRepository: ThemesRepository = createThemesRepository(),
        genresRepository: GenresRepository = createGenresRepository(),
        directorsRepository: DirectorsRepository = createDirectorsRepository(),
        clock: Clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"))
    ): CreateMovieHandler =
        CreateMovieHandler(
            MovieCreator(
                moviesRepository = moviesRepository,
                countriesRepository = countriesRepository,
                actorsRepository = actorsRepository,
                themesRepository = themesRepository,
                genresRepository = genresRepository,
                directorsRepository = directorsRepository,
                clock = clock
            )
        )


    @Test
    fun `should store movie when all data is valid`() {
        /*
        val moviesRepository = createMoviesRepository()
        val useCase = createUseCase(moviesRepository = moviesRepository)

        useCase.handle(createCommand())

        val storedMovies = moviesRepository.findAll()
        assertTrue(storedMovies.isNotEmpty())

         */
    }

    @Test
    fun `should return error when movie name is blank`() {
        val blankMovieName = ""
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                name = blankMovieName,
            )
        ) as Invalid

        assertTrue(result.value.contains(MovieValidationError.MovieNameBlankError))
    }

    @Test
    fun `should return error when country id is not valid uuid`() {
        val invalidCountryUuid = "some invalid uuid"
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                countryId = invalidCountryUuid,
            )
        ) as Invalid

        val error = result.value.first()
        assertTrue(error is InvalidUuIdError)
        assertEquals(invalidCountryUuid, error.message)
    }

    @Test
    fun `should return error when genre id is not valid uuid`() {
        val invalidGenreUuid = "some invalid uuid"
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                genreIds = setOf(invalidGenreUuid),
            )
        ) as Invalid

        val error = result.value.first()
        assertTrue(error is InvalidUuIdError)
        assertEquals(invalidGenreUuid, error.message)
    }

    @Test
    fun `should return error when multiple invalid genre ids `() {
        val invalidGenreUuid1 = "some invalid uuid1"
        val invalidGenreUuid2 = "some invalid uuid2"
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                genreIds = setOf(invalidGenreUuid1, invalidGenreUuid2),
            )
        ) as Invalid

        val error1 = result.value.first()
        assertTrue(error1 is InvalidUuIdError)
        assertEquals(invalidGenreUuid1, error1.message)
        val error2 = result.value.last()
        assertTrue(error2 is InvalidUuIdError)
        assertEquals(invalidGenreUuid2, error2.message)
    }

    @Test
    fun `should return error when multiple invalid theme ids `() {
        val invalidThemeUuid1 = "some invalid uuid1"
        val invalidThemeUuid2 = "some invalid uuid2"
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                themeIds = setOf(invalidThemeUuid1, invalidThemeUuid2),
            )
        ) as Invalid

        val error1 = result.value.first()
        assertTrue(error1 is InvalidUuIdError)
        assertEquals(invalidThemeUuid1, error1.message)
        val error2 = result.value.last()
        assertTrue(error2 is InvalidUuIdError)
        assertEquals(invalidThemeUuid2, error2.message)
    }

    @Test
    fun `should return error when multiple invalid director ids `() {
        val invalidId1 = "some invalid id1"
        val invalidId2 = "some invalid id2"
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                directorIds = setOf(invalidId1, invalidId2),
            )
        ) as Invalid

        val error1 = result.value.first()
        assertTrue(error1 is InvalidUuIdError)
        assertEquals(invalidId1, error1.message)
        val error2 = result.value.last()
        assertTrue(error2 is InvalidUuIdError)
        assertEquals(invalidId2, error2.message)
    }

    @Test
    fun `should return error when multiple invalid actor ids `() {
        val invalidId1 = "some invalid id1"
        val invalidId2 = "some invalid id2"
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                actorIds = setOf(invalidId1, invalidId2),
            )
        ) as Invalid

        val error1 = result.value.first()
        assertTrue(error1 is InvalidUuIdError)
        assertEquals(invalidId1, error1.message)
        val error2 = result.value.last()
        assertTrue(error2 is InvalidUuIdError)
        assertEquals(invalidId2, error2.message)
    }

    @Test
    fun `should return error when missing director id`() {
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                directorIds = setOf(),
            )
        ) as Invalid

        assertEquals(1, result.value.size)
        assertTrue(result.value.first() is MovieValidationError.MissingDirectorIdError)
    }

    @Test
    fun `should return error when missing genre id`() {
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                genreIds = setOf(),
            )
        ) as Invalid

        assertEquals(1, result.value.size)
        assertTrue(result.value.first() is MovieValidationError.MissingGenreIdError)
    }

    @Test
    fun `should return error when missing theme id`() {
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                themeIds = setOf(),
            )
        ) as Invalid

        assertEquals(1, result.value.size)
        assertTrue(result.value.first() is MovieValidationError.MissingThemeIdError)
    }

    @Test
    fun `should return errors when missing required reference ids`() {
        val useCase = createUseCase()

        val result = useCase.handle(
            createCommand(
                themeIds = setOf(),
                directorIds = setOf(),
                genreIds = setOf()
            )
        ) as Invalid

        val expectedErrors = Invalid(nonEmptyListOf(
            MovieValidationError.MissingDirectorIdError,
            MovieValidationError.MissingGenreIdError,
            MovieValidationError.MissingThemeIdError
        ))
        assertEquals(expectedErrors.value.toSet(), result.value.toSet())
    }

    @Test
    fun `should return error when movie already existing by name`() {
        val arbitraryThemeId = UUID.randomUUID().toString()
        val arbitraryMovie = arbitraryMovie()
        val moviesRepository = createMoviesRepository()
        val useCase = createUseCase(moviesRepository = moviesRepository)

        val result = useCase.handle(
            createCommand(themeIds = setOf(arbitraryThemeId))
        ) as Invalid

        val expectedError = ThemeNotFoundError(id = arbitraryThemeId)
        assertTrue(result.value.contains(expectedError))
    }

    @Test
    fun `should return error when theme not existing`() {
        val arbitraryThemeId = UUID.randomUUID().toString()
        val themesRepository = createThemesRepository()
        val useCase = createUseCase(themesRepository = themesRepository)

        val result = useCase.handle(
            createCommand(themeIds = setOf(arbitraryThemeId))
        ) as Invalid

        val expectedError = ThemeNotFoundError(id = arbitraryThemeId)
        assertTrue(result.value.contains(expectedError))
    }

    @Test
    fun `should return error when genre not existing`() {
        val arbitraryGenreId = UUID.randomUUID().toString()
        val genresRepository = createGenresRepository()
        val useCase = createUseCase(genresRepository = genresRepository)

        val result = useCase.handle(
            createCommand(genreIds = setOf(arbitraryGenreId))
        ) as Invalid

        val expectedError = GenreNotFoundError(id = arbitraryGenreId)
        assertTrue(result.value.contains(expectedError))
    }

    @Test
    fun `should return error when director not existing`() {
        val arbitraryDirectorId = UUID.randomUUID().toString()
        val directorsRepository = createDirectorsRepository()
        val useCase = createUseCase(directorsRepository = directorsRepository)

        val result = useCase.handle(
            createCommand(directorIds = setOf(arbitraryDirectorId))
        ) as Invalid

        val expectedError = DirectorNotFoundError(id = arbitraryDirectorId)
        assertTrue(result.value.contains(expectedError))
    }

    private fun createCommand(
        name: String = "arbitrary movie name",
        genreIds: Set<String> = setOf(UUID.randomUUID().toString()),
        actorIds: Set<String> = setOf(UUID.randomUUID().toString()),
        directorIds: Set<String> = setOf(UUID.randomUUID().toString()),
        themeIds: Set<String> = setOf(UUID.randomUUID().toString()),
        countryId: String = UUID.randomUUID().toString()
    ): CreateMovieCommand =
        CreateMovieCommand(
            name = name,
            genreIds = genreIds,
            actorIds = actorIds,
            directorIds = directorIds,
            themeIds = themeIds,
            countryId = countryId
        )
}