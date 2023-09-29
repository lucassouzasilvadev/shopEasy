package br.com.shopeasy.shopeasy.checkout

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest


@RestControllerAdvice
class CustomException {
    @ExceptionHandler(OrderException::class)
    fun handleOrderException(ex: OrderException, request: WebRequest): ResponseEntity<CustomErrorResponse> {
        val customErrorResponse = ex.toCustomErrorResponse()
        return ResponseEntity(customErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY)
    }

}

data class CustomErrorResponse(val message: String)
