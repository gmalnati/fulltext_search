package it.polito.search.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SearchRequestDTO(
    @field: NotBlank val text: String,
    @field:Min(1) val n: Int
)