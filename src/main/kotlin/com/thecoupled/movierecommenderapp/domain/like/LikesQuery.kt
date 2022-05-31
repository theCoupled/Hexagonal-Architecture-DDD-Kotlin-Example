package com.thecoupled.movierecommenderapp.domain.like

import com.thecoupled.movierecommenderapp.domain.user.UserId

data class LikesQuery(
    val userIds: Set<UserId>? = null
)