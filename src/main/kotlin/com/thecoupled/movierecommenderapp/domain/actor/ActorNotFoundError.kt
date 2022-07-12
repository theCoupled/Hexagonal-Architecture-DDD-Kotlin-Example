package com.thecoupled.movierecommenderapp.domain.actor

import com.thecoupled.movierecommenderapp.domain.shared.error.AggregateNotFoundError

class ActorNotFoundError(override val id: String) : AggregateNotFoundError(id)