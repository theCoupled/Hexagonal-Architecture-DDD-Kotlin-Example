package com.thecoupled.movierecommenderapp.domain.shared

import com.thecoupled.movierecommenderapp.domain.actor.Actor
import com.thecoupled.movierecommenderapp.domain.actor.ActorName
import com.thecoupled.movierecommenderapp.domain.actor.arbitraryActor
import com.thecoupled.movierecommenderapp.domain.country.Country
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.country.arbitraryCountry
import com.thecoupled.movierecommenderapp.domain.director.Director
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.director.arbitraryDirector
import com.thecoupled.movierecommenderapp.domain.genre.Genre
import com.thecoupled.movierecommenderapp.domain.genre.GenreName
import com.thecoupled.movierecommenderapp.domain.genre.arbitraryGenre
import com.thecoupled.movierecommenderapp.domain.like.arbitraryLike
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MovieDslBuilder
import com.thecoupled.movierecommenderapp.domain.movie.MovieDslData
import com.thecoupled.movierecommenderapp.domain.movie.MovieName
import com.thecoupled.movierecommenderapp.domain.movie.arbitraryEmptyMovie
import com.thecoupled.movierecommenderapp.domain.theme.Theme
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName
import com.thecoupled.movierecommenderapp.domain.theme.arbitraryTheme
import com.thecoupled.movierecommenderapp.domain.user.arbitraryUser

@DslMarker
annotation class UserCaseSetupBuilder

@UserCaseSetupBuilder
class UserCaseSetupDslBuilder {
    private val notViewedMoviesData: MutableSet<MovieDslData> = mutableSetOf()
    private val likedMoviesData: MutableSet<MovieDslData> = mutableSetOf()
    private val expectedRecommendations: MutableSet<MovieName> = mutableSetOf()

    fun notViewedMovie(block: MovieDslBuilder.() -> Unit) =
        notViewedMoviesData.add(
            MovieDslBuilder().apply(block).build()
        )

    fun likedMovie(block: MovieDslBuilder.() -> Unit) =
        likedMoviesData.add(
            MovieDslBuilder().apply(block).build()
        )


    fun build(): UserCaseSetup {
        val user = arbitraryUser()
        val countriesMap = (likedMoviesData + notViewedMoviesData)
            .map { arbitraryCountry(name = it.countryName) }
            .associateBy { it.name }
        val genresMap = (likedMoviesData + notViewedMoviesData)
            .flatMap { movieDslData -> movieDslData.genreNames.map { arbitraryGenre(name = it) } }
            .associateBy { it.name }
        val directorsMap = (likedMoviesData + notViewedMoviesData)
            .flatMap { movieDslData -> movieDslData.directorNames.map { arbitraryDirector(name = it) } }
            .associateBy { it.name }
        val actorsMap = (likedMoviesData + notViewedMoviesData)
            .flatMap { movieDslData -> movieDslData.actorNames.map { arbitraryActor(name = it) } }
            .associateBy { it.name }
        val themesMap = (likedMoviesData + notViewedMoviesData)
            .flatMap { movieDslData -> movieDslData.themeNames.map { arbitraryTheme(name = it) } }
            .associateBy { it.name }
        val likedMovies = likedMoviesData.map {
            it.toMovie(
                countriesMap = countriesMap,
                genresMap = genresMap,
                actorsMap = actorsMap,
                themesMap = themesMap,
                directorsMap = directorsMap
            )
        }
        val notViewedMovies = notViewedMoviesData.map {
            it.toMovie(
                countriesMap = countriesMap,
                genresMap = genresMap,
                actorsMap = actorsMap,
                themesMap = themesMap,
                directorsMap = directorsMap
            )
        }
        val likes = likedMovies.map { movie -> arbitraryLike(movieId = movie.id, userId = user.id) }

        return UserCaseSetup(
            user = user,
            movies = (likedMovies + notViewedMovies).toSet(),
            likes = likes.toSet()
        )
    }

    private fun MovieDslData.toMovie(
        countriesMap: Map<CountryName, Country>,
        genresMap: Map<GenreName, Genre>,
        actorsMap: Map<ActorName, Actor>,
        themesMap: Map<ThemeName, Theme>,
        directorsMap: Map<DirectorName, Director>
    ): Movie =
        arbitraryEmptyMovie(
            name = this.name,
            genreIds = this.genreNames.map { genresMap[it]!!.id }.toSet(),
            countryId = countriesMap[this.countryName]!!.id,
            themeIds = this.themeNames.map { themesMap[it]!!.id }.toSet(),
            actorIds = this.actorNames.map { actorsMap[it]!!.id }.toSet(),
            directorsIds = this.directorNames.map { directorsMap[it]!!.id }.toSet()
        )
}

fun userCaseSetup(setupBlock: UserCaseSetupDslBuilder.() -> Unit): UserCaseSetup =
    UserCaseSetupDslBuilder().apply(setupBlock).build()


