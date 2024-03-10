package it.polito.search.repositories

import it.polito.search.entities.Message
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: SearchRepository<Message, Long> {
}