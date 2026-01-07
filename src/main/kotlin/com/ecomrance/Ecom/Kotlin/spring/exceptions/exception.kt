package com.ecomrance.Ecom.Kotlin.spring.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


class CategoryCreationException(
    message: String,
    // You can optionally pass the underlying cause
    cause: Throwable? = null
) : Exception(message, cause) // Inherits from the base Exception class

class CategoryupdateException(
    message: String,
    // You can optionally pass the underlying cause
    cause: Throwable? = null
) : Exception(message, cause)



@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CategoryCreationException::class)
    fun handleCreationException(ex: CategoryCreationException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Category Creation Failed: ${ex.message}")
    }

    @ExceptionHandler(CategoryupdateException::class)
    fun handleUpdateException(ex: CategoryupdateException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Category Update Failed: ${ex.message}")
    }

    @ExceptionHandler(Exception::class) // fallback
    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Server Error: ${ex.message}")
    }
}