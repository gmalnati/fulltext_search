package it.polito.search.controllers

import it.polito.search.dtos.CreateMessageDTO
import it.polito.search.dtos.MessageDTO
import it.polito.search.dtos.SearchRequestDTO
import it.polito.search.exceptions.InvalidArgumentException
import it.polito.search.services.MessageService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestController
class MessageControlle(val messageService: MessageService) {
    @GetMapping("/search")
    fun searchMessages(
        @Valid searchRequestDTO: SearchRequestDTO,
        bindingResult: BindingResult
    ): List<MessageDTO> {
        println("DTO: {$searchRequestDTO} Errors: ${bindingResult.allErrors.toString()}")
        if (bindingResult.hasErrors())
            throw InvalidArgumentException(bindingResult.allErrors)
        with (searchRequestDTO) {
            return messageService.searchMessages(text, n)
        }
    }
    @PostMapping("/")
    fun addMessage(
        @Valid @RequestBody createMessageDTO: CreateMessageDTO,
        bindingResult: BindingResult
    ): MessageDTO {
        println("DTO: {$createMessageDTO} Errors: ${bindingResult.allErrors}")
        if (bindingResult.hasErrors())
            throw InvalidArgumentException(bindingResult.allErrors)
        with (createMessageDTO) {
            return messageService.addMessage(sender, subject, body)
        }
    }
}

@RestControllerAdvice
class ProblemDetailsHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(InvalidArgumentException::class)
    fun handleInvalidArgument(e: InvalidArgumentException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Invalid arguments"
        d.detail = e.errors.asSequence()
            .map {
                when (it) {
                    is FieldError -> "'${it.field}' ${it.defaultMessage}"
                    else -> it.defaultMessage
                }
            }
            .toList()
            .joinToString()
        return d
    }
}


