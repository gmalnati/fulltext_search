package it.polito.search.entities

import jakarta.persistence.*
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import java.time.Instant

@Entity
@Indexed
class Message(
    @FullTextField
    var sender: String,

    @FullTextField
    var subject: String,

    @FullTextField
    var body: String
)  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var date: Instant = Instant.now()
}