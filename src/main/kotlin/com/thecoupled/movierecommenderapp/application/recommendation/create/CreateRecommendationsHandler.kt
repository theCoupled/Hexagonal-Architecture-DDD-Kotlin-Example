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
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository

class CreateRecommendationsHandler(
    private val usersRepository: UsersRepository,
    private val likesRepository: LikesRepository,
    private val moviesRepository: MoviesRepository
) {

    fun execute(command: CreateRecommendationsCommand): Pair<List<Recommendation>, List<DomainEvent>> {
        val user = usersRepository.find(command.userId)
        val userLikes = likesRepository.query(LikesQuery(userIds = setOf(command.userId)))
        val likedMovies = moviesRepository.query(MoviesQuery(ids = userLikes.map(Like::movieId).toSet()))

        return Pair(listOf(), listOf())
    }

    private fun List<Movie>.getUserPattern(): UserPattern {
        val userPattern = UserPattern(
            actors = mutableMapOf(),
            countries = mutableMapOf(),
            directors = mutableMapOf(),
            genres = mutableMapOf(),
            themes = mutableMapOf(),
        )


        return userPattern
    }

    data class UserPattern(
        val actors: MutableMap<ActorId, Int>,
        val countries: MutableMap<CountryId, Int>,
        val directors: MutableMap<DirectorId, Int>,
        val genres: MutableMap<GenreId, Int>,
        val themes: MutableMap<ThemeId, Int>
    )
}