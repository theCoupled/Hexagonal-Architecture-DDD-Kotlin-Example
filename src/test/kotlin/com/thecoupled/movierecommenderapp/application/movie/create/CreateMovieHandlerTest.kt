package com.thecoupled.movierecommenderapp.application.movie.create

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.actor.ActorName
import com.thecoupled.movierecommenderapp.domain.actor.ActorNameEmptyException
import com.thecoupled.movierecommenderapp.domain.actor.ActorsRepository
import com.thecoupled.movierecommenderapp.domain.actor.arbitraryActor
import com.thecoupled.movierecommenderapp.domain.actor.createActorsRepository
import com.thecoupled.movierecommenderapp.domain.country.CountriesRepository
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.country.CountryNameEmptyException
import com.thecoupled.movierecommenderapp.domain.country.arbitraryCountry
import com.thecoupled.movierecommenderapp.domain.country.createCountriesRepository
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.director.DirectorNameEmptyException
import com.thecoupled.movierecommenderapp.domain.director.DirectorsRepository
import com.thecoupled.movierecommenderapp.domain.director.arbitraryDirector
import com.thecoupled.movierecommenderapp.domain.director.createDirectorsRepository
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.genre.GenreName
import com.thecoupled.movierecommenderapp.domain.genre.GenreNameEmptyException
import com.thecoupled.movierecommenderapp.domain.genre.GenresRepository
import com.thecoupled.movierecommenderapp.domain.genre.arbitraryGenre
import com.thecoupled.movierecommenderapp.domain.genre.createGenresRepository
import com.thecoupled.movierecommenderapp.domain.movie.MissingDirectorException
import com.thecoupled.movierecommenderapp.domain.movie.MissingGenreException
import com.thecoupled.movierecommenderapp.domain.movie.MissingThemeException
import com.thecoupled.movierecommenderapp.domain.movie.MovieAlreadyExistingException
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.movie.MovieNameEmptyException
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.arbitraryEnrichedMovie
import com.thecoupled.movierecommenderapp.domain.movie.createMoviesRepository
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName
import com.thecoupled.movierecommenderapp.domain.theme.ThemesRepository
import com.thecoupled.movierecommenderapp.domain.theme.arbitraryTheme
import com.thecoupled.movierecommenderapp.domain.theme.createThemesRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
            moviesRepository = moviesRepository,
            countriesRepository = countriesRepository,
            actorsRepository = actorsRepository,
            themesRepository = themesRepository,
            genresRepository = genresRepository,
            directorsRepository = directorsRepository,
            clock = clock
        )

    @Test
    fun `should throw exception when movie name is blank`() {
        val useCase = createUseCase()

        assertThrows<MovieNameEmptyException> {
            useCase.execute(
                createCommand(
                    name = MovieName("")
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie genre names are blank`() {
        val useCase = createUseCase()

        assertThrows<GenreNameEmptyException> {
            useCase.execute(
                createCommand(
                    genreNames = setOf(GenreName(""))
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie no genre name provided`() {
        val useCase = createUseCase()

        assertThrows<MissingGenreException> {
            useCase.execute(
                createCommand(
                    genreNames = setOf()
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie actor names are blank`() {
        val useCase = createUseCase()

        assertThrows<ActorNameEmptyException> {
            useCase.execute(
                createCommand(
                    actorNames = setOf(ActorName(""))
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie director names are blank`() {
        val useCase = createUseCase()

        assertThrows<DirectorNameEmptyException> {
            useCase.execute(
                createCommand(
                    directorNames = setOf(DirectorName(""))
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie no director name provided`() {
        val useCase = createUseCase()

        assertThrows<MissingDirectorException> {
            useCase.execute(
                createCommand(
                    directorNames = setOf()
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie theme names are blank`() {
        val useCase = createUseCase()

        assertThrows<DirectorNameEmptyException> {
            useCase.execute(
                createCommand(
                    directorNames = setOf(DirectorName(""))
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie no theme name provided`() {
        val useCase = createUseCase()

        assertThrows<MissingThemeException> {
            useCase.execute(
                createCommand(
                    themeNames = setOf()
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie country name is blank`() {
        val useCase = createUseCase()

        assertThrows<CountryNameEmptyException> {
            useCase.execute(
                createCommand(
                    countryName = CountryName("")
                )
            )
        }
    }

    @Test
    fun `should throw exception when movie name already existing`() {
        val existingMovieName = MovieName("existing movie name")
        val existingMovie = arbitraryEnrichedMovie(name = existingMovieName)
        val useCase = createUseCase(
            moviesRepository = createMoviesRepository(setOf(existingMovie))
        )

        assertThrows<MovieAlreadyExistingException> {
            useCase.execute(
                createCommand(
                    name = existingMovieName
                )
            )
        }
    }

    @Test
    fun `should create new country when country name not existing`() {
        val newCountryName = CountryName("arbitrary country name")
        val newCountryId = CountryId(UUID.randomUUID())
        val countriesRepo = createCountriesRepository(setOf(), setOf(newCountryId))
        val useCase = createUseCase(
            countriesRepository = countriesRepo
        )

        useCase.execute(
            createCommand(
                countryName = newCountryName
            )
        )

        val createdCountries = countriesRepo.findAll()
        val createdCountry = createdCountries.first()
        assertEquals(1, createdCountries.size)
        assertEquals(newCountryName, createdCountry.name)
        assertEquals(newCountryId, createdCountry.id)
    }

    @Test
    fun `should not create new country when country name already existing`() {
        val existingCountry = arbitraryCountry(name = CountryName("arbitrary existing country name"))
        val countriesRepo = createCountriesRepository(setOf(existingCountry))
        val useCase = createUseCase(countriesRepository = countriesRepo)

        useCase.execute(createCommand(countryName = CountryName(" ARBITRARY existing country NAME ")))

        val createdCountries = countriesRepo.findAll()
        val fetchedCountry = createdCountries.first()
        assertEquals(1, createdCountries.size)
        assertEquals(existingCountry.name, fetchedCountry.name)
        assertEquals(existingCountry.id, fetchedCountry.id)
    }

    @Test
    fun `should create new actor when actor name not existing`() {
        val newActorName = ActorName("arbitrary actor name")
        val newActorId = ActorId(UUID.randomUUID())
        val actorsRepo = createActorsRepository(setOf(), setOf(newActorId))
        val useCase = createUseCase(
            actorsRepository = actorsRepo
        )

        useCase.execute(
            createCommand(
                actorNames = setOf(newActorName)
            )
        )

        val createdCountries = actorsRepo.findAll()
        val createdCountry = createdCountries.first()
        assertEquals(1, createdCountries.size)
        assertEquals(newActorName, createdCountry.name)
        assertEquals(newActorId, createdCountry.id)
    }

    @Test
    fun `should not create new actor when actor name already existing`() {
        val existingActor = arbitraryActor(name = ActorName("arbitrary existing actor name"))
        val actorsRepo = createActorsRepository(setOf(existingActor))
        val useCase = createUseCase(actorsRepository = actorsRepo)

        useCase.execute(createCommand(actorNames = setOf(ActorName(" ARBITRARY existing actor NAME "))))

        val createdActors = actorsRepo.findAll()
        val fetchedActor = createdActors.first()
        assertEquals(1, createdActors.size)
        assertEquals(existingActor.name, fetchedActor.name)
        assertEquals(existingActor.id, fetchedActor.id)
    }

    @Test
    fun `should create new theme when theme name not existing`() {
        val newThemeName = ThemeName("arbitrary theme name")
        val newThemeId = ThemeId(UUID.randomUUID())
        val themesRepo = createThemesRepository(setOf(), setOf(newThemeId))
        val useCase = createUseCase(
            themesRepository = themesRepo
        )

        useCase.execute(
            createCommand(
                themeNames = setOf(newThemeName)
            )
        )

        val fetchedThemes = themesRepo.findAll()
        val fetchedTheme = fetchedThemes.first()
        assertEquals(1, fetchedThemes.size)
        assertEquals(newThemeName, fetchedTheme.name)
        assertEquals(newThemeId, fetchedTheme.id)
    }

    @Test
    fun `should not create new theme when theme name already existing`() {
        val existingTheme = arbitraryTheme(name = ThemeName("arbitrary existing theme name"))
        val themesRepo = createThemesRepository(setOf(existingTheme))
        val useCase = createUseCase(themesRepository = themesRepo)

        useCase.execute(createCommand(themeNames = setOf(ThemeName(" ARBITRARY existing theme NAME "))))

        val createdThemes = themesRepo.findAll()
        val fetchedTheme = createdThemes.first()
        assertEquals(1, createdThemes.size)
        assertEquals(existingTheme.name, fetchedTheme.name)
        assertEquals(existingTheme.id, fetchedTheme.id)
    }

    @Test
    fun `should create new genre when genre name not existing`() {
        val newGenreName = GenreName("arbitrary genre name")
        val newGenreId = GenreId(UUID.randomUUID())
        val genresRepo = createGenresRepository(setOf(), setOf(newGenreId))
        val useCase = createUseCase(
            genresRepository = genresRepo
        )

        useCase.execute(
            createCommand(
                genreNames = setOf(newGenreName)
            )
        )

        val fetchedGenres = genresRepo.findAll()
        val fetchedGenre = fetchedGenres.first()
        assertEquals(1, fetchedGenres.size)
        assertEquals(newGenreName, fetchedGenre.name)
        assertEquals(newGenreId, fetchedGenre.id)
    }

    @Test
    fun `should not create new genre when genre name already existing`() {
        val existingGenre = arbitraryGenre(name = GenreName("arbitrary existing genre name"))
        val genresRepo = createGenresRepository(setOf(existingGenre))
        val useCase = createUseCase(genresRepository = genresRepo)

        useCase.execute(createCommand(genreNames = setOf(GenreName(" ARBITRARY existing genre NAME "))))

        val createdGenres = genresRepo.findAll()
        val fetchedGenre = createdGenres.first()
        assertEquals(1, createdGenres.size)
        assertEquals(existingGenre.name, fetchedGenre.name)
        assertEquals(existingGenre.id, fetchedGenre.id)
    }

    @Test
    fun `should create new director when director name not existing`() {
        val newDirectorName = DirectorName("arbitrary Director name")
        val newDirectorId = DirectorId(UUID.randomUUID())
        val directorsRepo = createDirectorsRepository(setOf(), setOf(newDirectorId))
        val useCase = createUseCase(
            directorsRepository = directorsRepo
        )

        useCase.execute(
            createCommand(
                directorNames = setOf(newDirectorName)
            )
        )

        val fetchedDirectors = directorsRepo.findAll()
        val fetchedDirector = fetchedDirectors.first()
        assertEquals(1, fetchedDirectors.size)
        assertEquals(newDirectorName, fetchedDirector.name)
        assertEquals(newDirectorId, fetchedDirector.id)
    }

    @Test
    fun `should not create new director when director name already existing`() {
        val existingDirector = arbitraryDirector(name = DirectorName("arbitrary existing director name"))
        val directorsRepo = createDirectorsRepository(setOf(existingDirector))
        val useCase = createUseCase(directorsRepository = directorsRepo)

        useCase.execute(createCommand(directorNames = setOf(DirectorName(" ARBITRARY existing director NAME "))))

        val createdDirectors = directorsRepo.findAll()
        val fetchedDirector = createdDirectors.first()
        assertEquals(1, createdDirectors.size)
        assertEquals(existingDirector.name, fetchedDirector.name)
        assertEquals(existingDirector.id, fetchedDirector.id)
    }

    @Test
    fun `should create movie when not existing`() {
        val moviesRepo = createMoviesRepository()
        val useCase = createUseCase(moviesRepository = moviesRepo)

        useCase.execute(createCommand())

        val fetchedMovies = moviesRepo.findAll()
        assertEquals(1, fetchedMovies.size)
    }

    @Test
    fun `should create movie with same name as input when not existing`() {
        val movieNameString = "  Random MOVIE name  "
        val moviesRepo = createMoviesRepository()
        val useCase = createUseCase(moviesRepository = moviesRepo)

        useCase.execute(createCommand(name = MovieName(movieNameString)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(MovieName("random movie name"), fetchedMovie.name)
    }

    @Test
    fun `should create and return movie with same country id as input when country not existing`() {
        val countryName = CountryName("arbitrary country name")
        val countryId = CountryId(UUID.randomUUID())
        val moviesRepo = createMoviesRepository()
        val useCase = createUseCase(
            moviesRepository = moviesRepo,
            countriesRepository = createCountriesRepository(nextIds = setOf(countryId))
        )

        val (result, _) = useCase.execute(createCommand(countryName = countryName))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(countryId, fetchedMovie.countryId)
        assertEquals(countryId, result.countryId)
    }

    @Test
    fun `should create and return movie with same country id as input when country existing`() {
        val arbitraryCountry = arbitraryCountry()
        val moviesRepo = createMoviesRepository()
        val useCase = createUseCase(
            moviesRepository = moviesRepo,
            countriesRepository = createCountriesRepository(existingCountries = setOf(arbitraryCountry))
        )

        val (result, _) = useCase.execute(createCommand(countryName = arbitraryCountry.name))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(arbitraryCountry.id, fetchedMovie.countryId)
        assertEquals(arbitraryCountry.id, result.countryId)
    }

    @Test
    fun `should create and return movie with same genres as input when genres not existing`() {
        val genreName1 = GenreName("arbitrary genre 1")
        val genreName2 = GenreName("arbitrary genre 2")
        val genreId1 = GenreId(UUID.randomUUID())
        val genreId2 = GenreId(UUID.randomUUID())
        val moviesRepo = createMoviesRepository()
        val genresRepo = createGenresRepository(nextIds = setOf(genreId1, genreId2))
        val useCase = createUseCase(
            genresRepository = genresRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(genreNames = setOf(genreName1, genreName2)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(genreId1, genreId2), fetchedMovie.genreIds)
        assertEquals(setOf(genreId1, genreId2), result.genreIds)
    }

    @Test
    fun `should create and return movie with same genres as input when genres existing`() {
        val genre1 = arbitraryGenre(name = GenreName("genre 1"))
        val genre2 = arbitraryGenre(name = GenreName("genre 2"))
        val moviesRepo = createMoviesRepository()
        val genresRepo = createGenresRepository(existingGenres = setOf(genre1, genre2))
        val useCase = createUseCase(
            genresRepository = genresRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(genreNames = setOf(genre1.name, genre2.name)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(genre1.id, genre2.id), fetchedMovie.genreIds)
        assertEquals(setOf(genre1.id, genre2.id), result.genreIds)
    }

    @Test
    fun `should create and return movie with same directors as input when directors not existing`() {
        val directorName1 = DirectorName("arbitrary Director 1")
        val directorName2 = DirectorName("arbitrary Director 2")
        val directorId1 = DirectorId(UUID.randomUUID())
        val directorId2 = DirectorId(UUID.randomUUID())
        val moviesRepo = createMoviesRepository()
        val directorsRepo = createDirectorsRepository(nextIds = setOf(directorId1, directorId2))
        val useCase = createUseCase(
            directorsRepository = directorsRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(directorNames = setOf(directorName1, directorName2)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(directorId1, directorId2), fetchedMovie.directorIds)
        assertEquals(setOf(directorId1, directorId2), result.directorIds)
    }

    @Test
    fun `should create and return movie with same directors as input when directors existing`() {
        val director1 = arbitraryDirector(name = DirectorName("Director 1"))
        val director2 = arbitraryDirector(name = DirectorName("Director 2"))
        val moviesRepo = createMoviesRepository()
        val directorsRepo = createDirectorsRepository(existingDirectors = setOf(director1, director2))
        val useCase = createUseCase(
            directorsRepository = directorsRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(directorNames = setOf(director1.name, director2.name)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(director1.id, director2.id), fetchedMovie.directorIds)
        assertEquals(setOf(director1.id, director2.id), result.directorIds)
    }

    @Test
    fun `should create and return movie with same themes as input when themes not existing`() {
        val themeName1 = ThemeName("arbitrary Theme 1")
        val themeName2 = ThemeName("arbitrary Theme 2")
        val themeId1 = ThemeId(UUID.randomUUID())
        val themeId2 = ThemeId(UUID.randomUUID())
        val moviesRepo = createMoviesRepository()
        val themesRepo = createThemesRepository(nextIds = setOf(themeId1, themeId2))
        val useCase = createUseCase(
            themesRepository = themesRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(themeNames = setOf(themeName1, themeName2)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(themeId1, themeId2), fetchedMovie.themeIds)
        assertEquals(setOf(themeId1, themeId2), result.themeIds)
    }

    @Test
    fun `should create and return movie with same themes as input when themes existing`() {
        val theme1 = arbitraryTheme(name = ThemeName("Theme 1"))
        val theme2 = arbitraryTheme(name = ThemeName("Theme 2"))
        val moviesRepo = createMoviesRepository()
        val themesRepo = createThemesRepository(existingThemes = setOf(theme1, theme2))
        val useCase = createUseCase(
            themesRepository = themesRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(themeNames = setOf(theme1.name, theme2.name)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(theme1.id, theme2.id), fetchedMovie.themeIds)
        assertEquals(setOf(theme1.id, theme2.id), result.themeIds)
    }

    @Test
    fun `should create and return movie with same actors as input when actors not existing`() {
        val actorName1 = ActorName("arbitrary Actor 1")
        val actorName2 = ActorName("arbitrary Actor 2")
        val actorId1 = ActorId(UUID.randomUUID())
        val actorId2 = ActorId(UUID.randomUUID())
        val moviesRepo = createMoviesRepository()
        val actorsRepo = createActorsRepository(nextIds = setOf(actorId1, actorId2))
        val useCase = createUseCase(
            actorsRepository = actorsRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(actorNames = setOf(actorName1, actorName2)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(actorId1, actorId2), fetchedMovie.actorIds)
        assertEquals(setOf(actorId1, actorId2), result.actorIds)
    }

    @Test
    fun `should create and return movie with same actors as input when actors existing`() {
        val actor1 = arbitraryActor(name = ActorName("Actor 1"))
        val actor2 = arbitraryActor(name = ActorName("Actor 2"))
        val moviesRepo = createMoviesRepository()
        val actorsRepo = createActorsRepository(existingActors = setOf(actor1, actor2))
        val useCase = createUseCase(
            actorsRepository = actorsRepo,
            moviesRepository = moviesRepo
        )

        val (result, _) = useCase.execute(createCommand(actorNames = setOf(actor1.name, actor2.name)))

        val fetchedMovie = moviesRepo.findAll().first()
        assertEquals(setOf(actor1.id, actor2.id), fetchedMovie.actorIds)
        assertEquals(setOf(actor1.id, actor2.id), result.actorIds)
    }

    private fun createCommand(
        name: MovieName = MovieName("arbitrary movie name"),
        genreNames: Set<GenreName> = setOf(GenreName("arbitrary genre name")),
        actorNames: Set<ActorName> = setOf(ActorName("arbitrary actor name")),
        directorNames: Set<DirectorName> = setOf(DirectorName("arbitrary director name")),
        themeNames: Set<ThemeName> = setOf(ThemeName("arbitrary theme name")),
        countryName: CountryName = CountryName("arbitrary country name")
    ): CreateMovieCommand =
        CreateMovieCommand(
            name = name,
            genreNames = genreNames,
            actorNames = actorNames,
            directorNames = directorNames,
            themeNames = themeNames,
            countryName = countryName
        )
}