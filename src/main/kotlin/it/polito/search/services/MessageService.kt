package it.polito.search.services

import it.polito.search.dtos.MessageDTO
import it.polito.search.dtos.toDTO
import it.polito.search.entities.Message
import it.polito.search.repositories.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(val messageRepository: MessageRepository) {
    fun searchMessages(text: String, limit: Int, fields: List<String> = SEARCHABLE_FIELDS): List<MessageDTO> {
        val invalidFields = fields.asSequence().filter { ! SEARCHABLE_FIELDS.contains(it) }.toList()
        if (invalidFields.isNotEmpty()) throw IllegalArgumentException(invalidFields.joinToString { it })
        return messageRepository.searchBy(text, limit, *fields.toTypedArray()).map { it.toDTO()}
    }

    fun addMessage(from: String, subject: String, body: String): MessageDTO {
        return messageRepository.save(Message(from, subject, body)).toDTO()
    }
    companion object {
        val SEARCHABLE_FIELDS = listOf("sender", "subject", "body")
    }
}