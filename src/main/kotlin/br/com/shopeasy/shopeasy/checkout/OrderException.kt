package br.com.shopeasy.shopeasy.checkout

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class OrderException(message: String?) : RuntimeException(message) {


    fun toCustomErrorResponse(): CustomErrorResponse {
        return CustomErrorResponse(this.message!!)
    }

}