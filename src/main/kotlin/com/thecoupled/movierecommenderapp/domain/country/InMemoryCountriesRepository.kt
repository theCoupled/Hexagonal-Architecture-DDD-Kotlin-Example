package com.thecoupled.movierecommenderapp.domain.country


import com.thecoupled.movierecommenderapp.domain.shared.InMemoryRepository
import org.springframework.stereotype.Repository
import java.util.*
@Repository
class InMemoryCountriesRepository(
    private val existing: Set<Country> = setOf(),
    private val nextIds: MutableList<CountryId> = mutableListOf()
) : InMemoryRepository<Country, CountryId>(existing, nextIds), CountriesRepository {

    override fun query(query: CountriesQuery): List<Country> {
        val returnList: MutableList<Country> = mutableListOf()

        if (query.names != null) {
            returnList += collection.values.filter { entity -> query.names!!.contains(entity.name) }
        }

        return returnList
    }

    override fun nextId(): CountryId = nextIds.removeFirstOrNull() ?: CountryId(UUID.randomUUID())
}

fun createCountriesRepository(
    existingCountries: Set<Country> = setOf(),
    nextIds: Set<CountryId> = setOf()
): CountriesRepository = InMemoryCountriesRepository(existingCountries, nextIds.toMutableList())