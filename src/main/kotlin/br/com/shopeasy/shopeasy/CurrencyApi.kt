package br.com.shopeasy.shopeasy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ThreadLocalRandom

@RestController
@RequestMapping("/currency")
class CurrencyApi {

    @GetMapping
    fun getCurrency(): Double{
        return 3.0 + ThreadLocalRandom.current().nextDouble(1.0, 9.0)
    }
}