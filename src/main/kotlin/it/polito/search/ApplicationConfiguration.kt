package it.polito.search

import it.polito.search.repositories.SearchRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(repositoryBaseClass = SearchRepositoryImpl::class)
class ApplicationConfiguration {
}