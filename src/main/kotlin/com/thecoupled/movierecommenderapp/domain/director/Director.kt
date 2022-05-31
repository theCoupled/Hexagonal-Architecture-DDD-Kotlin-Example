package com.thecoupled.movierecommenderapp.domain.director

import com.thecoupled.movierecommenderapp.domain.shared.Aggregate

data class Director(
    override val id: DirectorId,
    val name: DirectorName
) : Aggregate