package br.com.shopeasy.shopeasy.order

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class OrderException(message: String?) : RuntimeException(message) {

    fun toCustomErrorResponse(): CustomErrorResponse {
        return CustomErrorResponse(this.message!!)
    }

}