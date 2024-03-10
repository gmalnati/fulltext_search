package it.polito.search.exceptions

import org.springframework.validation.ObjectError

class InvalidArgumentException(val errors: List<ObjectError>): RuntimeException("Invalid arguments") {
}