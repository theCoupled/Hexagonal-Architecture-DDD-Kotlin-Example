package com.thecoupled.movierecommenderapp.domain.recommendation

import com.thecoupled.movierecommenderapp.domain.actor.ActorId
import com.thecoupled.movierecommenderapp.domain.country.CountryId
import com.thecoupled.movierecommenderapp.domain.director.Director
import com.thecoupled.movierecommenderapp.domain.genre.GenreId
import com.thecoupled.movierecommenderapp.domain.theme.ThemeId

data class RecommendationInfo(
    val actorIds: Set<ActorId>,
    val directorIds: Set<Director>,
    val genreIds: Set<GenreId>,
    val themeIds: Set<ThemeId>,
    val countryIds: Set<CountryId>
)