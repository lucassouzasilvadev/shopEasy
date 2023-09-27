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
        val outPut = OutPut(total = 0.0, frete = 0.0)
        if (!ValidateCpf(input.cpf).isValidCpf()) throw OrderException("cpf is invalid")
        if (input.items.isNotEmpty()){
            input.items.forEach { products ->
                val product = productRepository.getReferenceById(products.idProduct)
                if (product.largura < 0.0 || product.altura < 0.0 || product.profundidade < 0.0){
                    throw OrderException("Nenhuma dimensão do item pode ser negativa")
                }
                if (product.peso < 0.0){
                    throw OrderException("O peso do item não pode ser negativo")
                }
                val distinct = input.items.distinctBy { it.idProduct }.count()
                if (distinct < input.items.size) throw OrderException("existe algum item duplicado na lista")
                if (products.quantity < 1) throw OrderException("pedido não pode ter quantidade negativa")
                val volume = product.altura*product.largura*product.profundidade
                val densidade = product.peso/volume
                var frete = 1000 * volume * (densidade/ 100.0)
//                if (frete < BigDecimal(10)) {
//                    frete = BigDecimal(10)
//                }
                outPut.frete = frete
                outPut.total += product.price * products.quantity + frete
            }
        }
        if (!input.coupon.isNullOrBlank()){
            val coupon = couponRepository.findByCode(input.coupon!!)
            val formattedMonth = if (LocalDate.now().monthValue < 10) "0${LocalDate.now().monthValue}" else "${LocalDate.now().monthValue}"
            val today = "${LocalDate.now().year}$formattedMonth${LocalDate.now().dayOfMonth}"
            if (coupon.validate < today.toInt()) throw OrderException("cupom está expirado")
            outPut.total -= outPut.total * coupon.percentage / 100
        }
        return outPut
    }

}
