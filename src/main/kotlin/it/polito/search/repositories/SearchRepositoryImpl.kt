package it.polito.search.repositories

import jakarta.persistence.EntityManager
import org.hibernate.search.engine.search.query.SearchResult
import org.hibernate.search.engine.search.query.dsl.SearchQueryOptionsStep
import org.hibernate.search.engine.search.query.dsl.SearchQuerySelectStep
import org.hibernate.search.mapper.orm.Search
import org.hibernate.search.mapper.orm.session.SearchSession
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable


@Suppress("UNCHECKED_CAST")
@Transactional
class SearchRepositoryImpl<T, ID : Serializable> : SimpleJpaRepository<T, ID>, SearchRepository<T, ID> {
    lateinit private var entityManager: EntityManager

    constructor(domainClass: Class<T>?, entityManager: EntityManager) : super(domainClass!!, entityManager) {
        this.entityManager = entityManager
    }

    constructor(
        entityInformation: JpaEntityInformation<T, ID>?,
        entityManager: EntityManager
    ) : super(entityInformation!!, entityManager) {
        this.entityManager = entityManager
    }

    override fun searchBy(text: String, limit: Int, vararg fields: String): List<T> {
        val result: SearchResult<out T> = getSearchResult(text, limit, fields)
        return result.hits()
    }


    private fun getSearchResult(text: String, limit: Int, fields: Array<out String>): SearchResult<out T> {
        val searchSession: SearchSession = Search.session(entityManager)

        val result: SearchResult<out T> =
             (searchSession.search<T>(domainClass)
                as SearchQuerySelectStep<out SearchQueryOptionsStep<*, T, *, *, *>, T, *, *, *, * >)
            .where { f ->
                f
                    .match()
                    .fields(*fields)
                    .matching(text)
                    .fuzzy(2)
            }
            .fetch(limit)
        return result
    }
}