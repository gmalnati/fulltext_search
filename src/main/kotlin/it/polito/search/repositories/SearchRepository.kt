package it.polito.search.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface SearchRepository<T, ID: Serializable> : JpaRepository<T,ID> {
    fun searchBy(text: String, limit: Int, vararg fields:String): List<T>
}