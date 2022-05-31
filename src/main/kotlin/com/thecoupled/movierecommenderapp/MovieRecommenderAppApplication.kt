package com.thecoupled.movierecommenderapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MovieRecommenderAppApplication

fun main(args: Array<String>) {
	runApplication<MovieRecommenderAppApplication>(*args)
}
