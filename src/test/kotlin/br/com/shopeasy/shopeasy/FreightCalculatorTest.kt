package br.com.shopeasy.shopeasy

import br.com.shopeasy.shopeasy.checkout.FreightCalculator
import br.com.shopeasy.shopeasy.checkout.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FreightCalculatorTest {

    @Test
    fun `deve calcular o frete do produto`(){
        val freight = FreightCalculator().calculate(
            Product(id_product = 5, description = "vazia", price = 1000.0, height = 30.0, width = 100.0, length = 10.0, weight = 3.0, currency = "USD" )
        )
        assertEquals(30.0, freight)
    }
}