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
import com.thecoupled.movierecommenderapp.domain.recommendation.MovieRecommendationDslData
import com.thecoupled.movierecommenderapp.domain.recommendation.RecommendationScore
import com.thecoupled.movierecommenderapp.domain.recommendation.arbitraryMovieRecommendation
import com.thecoupled.movierecommenderapp.domain.recommendation.arbitraryRecommendation
import com.thecoupled.movierecommenderapp.domain.theme.Theme
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName
import com.thecoupled.movierecommenderapp.domain.theme.arbitraryTheme
import com.thecoupled.movierecommenderapp.domain.user.arbitraryUser

@DslMarker
annotation class UserCaseSetupBuilder

@UserCaseSetupBuilder
class UserCaseSetupDslBuilder {
    private var notViewedMoviesData: Set<MovieDslData> = setOf()
    private var likedMoviesData: Set<MovieDslData> = setOf()
    private var expectedMovieRecommendations: Set<MovieRecommendationDslData> = setOf()

    fun likedMovies(block: MovieCollectionDslBuilder.() -> Unit) {
        likedMoviesData = MovieCollectionDslBuilder().apply(block).build()
    }

    fun notViewedMovies(block: MovieCollectionDslBuilder.() -> Unit) {
        notViewedMoviesData = MovieCollectionDslBuilder().apply(block).build()
    }

    fun expectedMovieRecommendations(block: RecommendationCollectionDslBuilder.() -> Unit) {
        expectedMovieRecommendations = RecommendationCollectionDslBuilder().apply(block).build()
    }

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
        }.associateBy { it.name }
        val notViewedMovies = notViewedMoviesData.map {
            it.toMovie(
                countriesMap = countriesMap,
                genresMap = genresMap,
                actorsMap = actorsMap,
                themesMap = themesMap,
                directorsMap = directorsMap
            )
        }.associateBy { it.name }
        val likes = likedMovies.values.map { movie -> arbitraryLike(movieId = movie.id, userId = user.id) }

        return UserCaseSetup(
            user = user,
            movies = (likedMovies + notViewedMovies).values.toSet(),
            likes = likes.toSet(),
            expectedRecommendation = arbitraryRecommendation(
                movieRecommendations = expectedMovieRecommendations.map { recommendationDslData ->
                    arbitraryMovieRecommendation(
                        movieId = notViewedMovies[recommendationDslData.movieName]!!.id,
                        score = recommendationDslData.score
                    )
                }.toSet()
            )
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

class MovieCollectionDslBuilder {
    private val movies = mutableSetOf<MovieDslData>()
    fun movie(block: MovieDslBuilder.() -> Unit) =
        movies.add(
            MovieDslBuilder().apply(block).build()
        )

    fun build(): Set<MovieDslData> = movies
}

class RecommendationCollectionDslBuilder {
    private val recommendations = mutableSetOf<MovieRecommendationDslData>()

    fun recommendation(movie: String, score: Double) =
        recommendations.add(
            MovieRecommendationDslData(
                movieName = MovieName(movie),
                score = RecommendationScore(score.toFloat())
            )
        )

    fun build(): Set<MovieRecommendationDslData> = recommendations
}


fun userCaseSetup(setupBlock: UserCaseSetupDslBuilder.() -> Unit): UserCaseSetup =
    UserCaseSetupDslBuilder().apply(setupBlock).build()


