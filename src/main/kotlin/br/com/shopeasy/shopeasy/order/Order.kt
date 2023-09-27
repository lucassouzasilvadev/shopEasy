package br.com.shopeasy.shopeasy.order

import br.com.shopeasy.shopeasy.validate.ValidateCpf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@RequestMapping("/order")
class Order {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var couponRepository: CouponRepository

    @PostMapping
    fun calculateOrder(@RequestBody input: Input): OutPut{
        try {
            val outPut = OutPut(total = 0.0, freight = 0.0)
            if (!ValidateCpf(input.cpf).isValidCpf()) throw OrderException("cpf is invalid")
            if (input.items.isNotEmpty()){
                input.items.forEach { items ->
                    val product = productRepository.getReferenceById(items.idProduct)
                    if (product.width <= 0.0 || product.height <= 0.0 || product.length <= 0.0 || product.weight < 0.0){
                        throw OrderException("invalid dimension")
                    }
                    val distinct = input.items.distinctBy { it.idProduct }.count()
                    if (distinct < input.items.size) throw OrderException("duplicated item")
                    if (items.quantity <= 0) throw OrderException("invalid quantity")
                    val volume = product.height/100 * product.width/100 * product.length/100
                    val density = product.weight/volume
                    var itemFreight = 1000 * volume * (density/ 100.0)
                    if (itemFreight < 10.0) {
                        itemFreight = 10.0
                    }
                    outPut.freight += itemFreight * items.quantity
                    outPut.total += product.price * items.quantity
                }
            }
            if (!input.coupon.isNullOrBlank()){
                val coupon = couponRepository.findByCode(input.coupon!!)
                val formattedMonth = if (LocalDate.now().monthValue < 10) "0${LocalDate.now().monthValue}" else "${LocalDate.now().monthValue}"
                val today = "${LocalDate.now().year}$formattedMonth${LocalDate.now().dayOfMonth}"
                if (coupon.validate > today.toInt()){
                    outPut.total -= outPut.total * coupon.percentage / 100
                }
            }
            if (!input.from.isNullOrBlank() && !input.to.isNullOrBlank()){
                outPut.total += outPut.freight
            }
            return outPut
        }catch (e: OrderException){
            throw OrderException(e.message)
        }
    }

}
