package com.thecoupled.movierecommenderapp.application.shared

import com.thecoupled.movierecommenderapp.domain.shared.AggregateId

interface Handler<C: Command, A: AggregateId> {
    fun handle(command: C): A
}

