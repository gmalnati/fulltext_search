package it.polito.search.dtos

import jakarta.validation.constraints.NotBlank

data class CreateMessageDTO(
    @field:NotBlank val sender: String,
    @field:NotBlank val subject: String,
    @field:NotBlank val body: String
)