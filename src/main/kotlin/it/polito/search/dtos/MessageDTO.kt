package it.polito.search.dtos

import it.polito.search.entities.Message
import java.time.Instant

data class MessageDTO(
    val sender: String,
    val subject: String,
    val body: String,
    val date: Instant,
    val id: Long
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(sender, subject, body, date, id!!)
}
