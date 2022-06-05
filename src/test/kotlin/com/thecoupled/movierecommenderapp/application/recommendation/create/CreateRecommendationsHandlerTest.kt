package com.thecoupled.movierecommenderapp.application.recommendation.create

import com.thecoupled.movierecommenderapp.domain.like.LikesRepository
import com.thecoupled.movierecommenderapp.domain.like.createLikesRepository
import com.thecoupled.movierecommenderapp.domain.movie.MoviesRepository
import com.thecoupled.movierecommenderapp.domain.movie.createMoviesRepository
import com.thecoupled.movierecommenderapp.domain.recommendation.Recommendation
import com.thecoupled.movierecommenderapp.domain.shared.DomainEvent
import com.thecoupled.movierecommenderapp.domain.shared.UserCaseSetup
import com.thecoupled.movierecommenderapp.domain.shared.userCaseSetup
import com.thecoupled.movierecommenderapp.domain.user.UsersRepository
import com.thecoupled.movierecommenderapp.domain.user.createUsersRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CreateRecommendationsHandlerTest {

    @Test
    fun `should not recommend any movie when no movies available`() {
        val userCaseSetup = userCaseSetup {
            likedMovie {
                name = "arbitrary movie name"
                country = "arbitrary country name"
                genres { genre("arbitrary genre") }
                directors { director("arbitrary director") }
                actors { }
                themes { theme("arbitrary theme") }
            }
        }

        val (result, _) = executeWithUserCaseSetup(userCaseSetup)

        assertTrue(result.isEmpty())
    }

    private fun executeWithUserCaseSetup(userCaseSetup: UserCaseSetup): Pair<List<Recommendation>, List<DomainEvent>> =
        createUseCase(
            usersRepository = createUsersRepository(setOf(userCaseSetup.user)),
            moviesRepository = createMoviesRepository(userCaseSetup.movies),
            likesRepository = createLikesRepository(userCaseSetup.likes)
        ).execute(CreateRecommendationsCommand(userCaseSetup.user.id))

    private fun createUseCase(
        usersRepository: UsersRepository = createUsersRepository(),
        moviesRepository: MoviesRepository = createMoviesRepository(),
        likesRepository: LikesRepository = createLikesRepository()
    ): CreateRecommendationsHandler =
        CreateRecommendationsHandler(
            usersRepository = usersRepository,
            moviesRepository = moviesRepository,
            likesRepository = likesRepository
        )
}