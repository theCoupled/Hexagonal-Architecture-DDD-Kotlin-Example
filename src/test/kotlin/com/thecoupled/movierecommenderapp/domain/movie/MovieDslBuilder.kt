package com.thecoupled.movierecommenderapp.domain.movie

import com.thecoupled.movierecommenderapp.domain.actor.ActorName
import com.thecoupled.movierecommenderapp.domain.country.CountryName
import com.thecoupled.movierecommenderapp.domain.director.DirectorName
import com.thecoupled.movierecommenderapp.domain.genre.GenreName
import com.thecoupled.movierecommenderapp.domain.shared.UserCaseSetupBuilder
import com.thecoupled.movierecommenderapp.domain.theme.ThemeName

@UserCaseSetupBuilder
class MovieDslBuilder {
    var name: String? = null
    var country: String? = null
    private val genreNames: MutableSet<GenreName> = mutableSetOf()
    private val themeNames: MutableSet<ThemeName> = mutableSetOf()
    private val actorNames: MutableSet<ActorName> = mutableSetOf()
    private val directorNames: MutableSet<DirectorName> = mutableSetOf()

    fun genres(block: GenreNamesDslBuilder.()-> Unit) =
        GenreNamesDslBuilder().apply(block).build()
            .forEach { name -> genreNames.add(name) }

    fun directors(block: DirectorNamesDslBuilder.()-> Unit) =
        DirectorNamesDslBuilder().apply(block).build()
            .forEach { name -> directorNames.add(name) }

    fun actors(block: ActorNamesDslBuilder.()-> Unit) =
        ActorNamesDslBuilder().apply(block).build()
            .forEach { name -> actorNames.add(name) }

    fun themes(block: ThemeNamesDslBuilder.()-> Unit) =
        ThemeNamesDslBuilder().apply(block).build()
            .forEach { name -> themeNames.add(name) }

    fun build(): MovieDslData =
        MovieDslData(
            name = MovieName(name!!),
            genreNames = genreNames,
            actorNames = actorNames,
            directorNames = directorNames,
            themeNames = themeNames,
            countryName = CountryName(country!!)
        )
}

@UserCaseSetupBuilder
class GenreNamesDslBuilder {
    private val genreNames: MutableSet<GenreName> = mutableSetOf()

    fun genre(name: String) {
        genreNames.add(GenreName(name))
    }
    fun build(): Set<GenreName> = genreNames
}

@UserCaseSetupBuilder
class DirectorNamesDslBuilder {
    private val names: MutableSet<DirectorName> = mutableSetOf()

    fun director(name: String) {
        names.add(DirectorName(name))
    }
    fun build(): Set<DirectorName> = names
}

@UserCaseSetupBuilder
class ActorNamesDslBuilder {
    private val names: MutableSet<ActorName> = mutableSetOf()

    fun actor(name: String) {
        names.add(ActorName(name))
    }
    fun build(): Set<ActorName> = names
}

@UserCaseSetupBuilder
class ThemeNamesDslBuilder {
    private val names: MutableSet<ThemeName> = mutableSetOf()

    fun theme(name: String) {
        names.add(ThemeName(name))
    }
    fun build(): Set<ThemeName> = names
}