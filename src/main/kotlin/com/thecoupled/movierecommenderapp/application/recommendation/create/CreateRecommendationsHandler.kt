package com.thecoupled.movierecommenderapp.application.recommendation.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.CommandHandler
import com.thecoupled.movierecommenderapp.domain.recommendation.RecommendationId
import com.thecoupled.movierecommenderapp.domain.user.UserId
import java.util.*

class CreateRecommendationsHandler(
    private val recommendationCreator: RecommendationCreator
) : CommandHandler<CreateRecommendationsCommand, RecommendationId> {
    override fun handle(command: CreateRecommendationsCommand): RecommendationId =
        recommendationCreator.create(userId = UserId(UUID.fromString(command.userId))).first.id
}