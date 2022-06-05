package com.thecoupled.movierecommenderapp.application.recommendation.create

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.DirectorId
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.like.Like
import com.thecoupled.movierecommenderapp.domain.like.LikesQuery
import com.thecoupled.movierecommenderapp.domain.like.LikesRepository
import com.thecoupled.movierecommenderapp.domain.movie.Movie
import com.thecoupled.movierecommenderapp.domain.movie.MoviesQuery
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.recommendation.Recommendation
import com.thecoupled.movierecommenderapp.domain.recommendation.RecommendationInfo
import com.thecoupled.movierecommenderapp.domain.recommendation.RecommendationScore
import com.thecoupled.movierecommenderapp.domain.recommendation.RecommendationsRepository
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import com.thecoupled.movierecommenderapp.domain.user.UserNotFoundException
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository

class CreateRecommendationsHandler(
    private val usersRepository: UsersRepository,
    private val likesRepository: LikesRepository,
    private val moviesRepository: MoviesRepository,
    private val recommendationsRepository: RecommendationsRepository
) {

    fun execute(command: CreateRecommendationsCommand): Pair<List<Recommendation>, List<DomainEvent>> {
        val user = usersRepository.find(command.userId) ?: throw UserNotFoundException()
        val userLikes = likesRepository.query(LikesQuery(userIds = setOf(command.userId)))
        val likedMovies = moviesRepository.query(MoviesQuery(andIds = userLikes.map(Like::movieId).toSet()))
        val userPattern = likedMovies.getUserAffinityPattern()
        val moviesWithAffinity = moviesRepository.query(
            MoviesQuery(
                andExcludeIds = likedMovies.map(Movie::id).toSet(),
                orActorIds = userPattern.actors.keys,
                orDirectorIds = userPattern.directors.keys,
                orGenreIds = userPattern.genres.keys,
                orCountryIds = userPattern.countries.keys,
                orThemeIds = userPattern.themes.keys
            )
        )
        val recommendations = moviesWithAffinity.map { movie ->
            Recommendation(
                id = recommendationsRepository.nextId(),
                score = RecommendationScore(0.0f),
                movieId = movie.id,
                info = RecommendationInfo(
                    actorIds = setOf(),
                    directorIds = setOf(),
                    genreIds = setOf(),
                    themeIds = setOf(),
                    countryIds = setOf()
                )
            )
        }

        return Pair(recommendations, listOf())
    }

    private fun List<Movie>.getUserAffinityPattern(): UserPattern {
        val userPattern = UserPattern(
            actors = mutableMapOf(),
            countries = mutableMapOf(),
            directors = mutableMapOf(),
            genres = mutableMapOf(),
            themes = mutableMapOf()
        )

        this.forEach { movie ->
            userPattern.addCountry(movie.countryId)
            movie.actorIds.forEach { actorId -> userPattern.addActor(actorId) }
            movie.directorIds.forEach { directorId -> userPattern.addDirector(directorId) }
            movie.genreIds.forEach { genreId -> userPattern.addGenre(genreId) }
            movie.themeIds.forEach { themeId -> userPattern.addTheme(themeId) }
        }


        return userPattern
    }

    data class UserPattern(
        val actors: MutableMap<ActorId, Int>,
        val countries: MutableMap<CountryId, Int>,
        val directors: MutableMap<DirectorId, Int>,
        val genres: MutableMap<GenreId, Int>,
        val themes: MutableMap<ThemeId, Int>
    ) {

        fun addCountry(countryId: CountryId) {
            this.countries[countryId] = this.countries.getOrDefault(countryId, 0) + 1
        }

        fun addActor(actorId: ActorId) {
            this.actors[actorId] = this.actors.getOrDefault(actorId, 0) + 1
        }

        fun addGenre(genreId: GenreId) {
            this.genres[genreId] = this.genres.getOrDefault(genreId, 0) + 1
        }

        fun addTheme(themeId: ThemeId) {
            this.themes[themeId] = this.themes.getOrDefault(themeId, 0) + 1
        }

        fun addDirector(directorId: DirectorId) {
            this.directors[directorId] = this.directors.getOrDefault(directorId, 0) + 1
        }
    }
}