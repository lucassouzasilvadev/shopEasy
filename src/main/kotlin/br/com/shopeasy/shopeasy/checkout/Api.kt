package br.com.shopeasy.shopeasy.checkout

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/order")
class Api {

    @Autowired
    lateinit var checkout: Checkout

    @PostMapping
    fun calculateOrder(@RequestBody input: Input): OutPut{
        try {
           return checkout.execute(input)
        }catch (e: OrderException){
            throw OrderException(e.message)
        }
    }

}
