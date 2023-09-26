package br.com.shopeasy.shopeasy.order

import br.com.shopeasy.shopeasy.validate.ValidateCpf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal


@RestController
@RequestMapping("/order")
class Order {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var couponRepository: CouponRepository

    @PostMapping
    fun calculateOrder(@RequestBody input: Input): OutPut{
        val outPut = OutPut(total = BigDecimal.ZERO)
        if (!ValidateCpf(input.cpf).isValidCpf()) throw OrderException("cpf is invalid")
        if (input.items.isNotEmpty()){
            for (item in input.items){
                val product = productRepository.getReferenceById(item.idProduct)
                outPut.total += product.price * item.quantity.toBigDecimal()
            }
        }
        if (!input.coupon.isNullOrBlank()){
            val coupon = couponRepository.findByCode(input.coupon!!)
            outPut.total -= outPut.total * coupon.percentage / BigDecimal(100)
        }
        return outPut
    }

}