package com.thecoupled.movierecommenderapp.application.recommendation.create

import com.thecoupled.movierecommenderapp.application.shared.bus.command.Command

class CreateRecommendationsCommand(
    val userId: String
): Command