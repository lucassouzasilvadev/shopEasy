package br.com.shopeasy.shopeasy.checkout

import br.com.shopeasy.shopeasy.validate.ValidateCpf
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Checkout(val productRepository: ProductRepository,
               val couponRepository: CouponRepository,
               val currecyGateway: CurrencyGateway
    ) {


    fun execute(input: Input): OutPut{
        val outPut = OutPut(total = 0.0, freight = 0.0)
        if (!ValidateCpf(input.cpf).isValidCpf()) throw OrderException("cpf is invalid")
        val currencies = currecyGateway.getCurrencies()
        if (input.items.isNotEmpty()){
            input.items.forEach { items ->
                val product = productRepository.findProductById(items.idProduct)
                if (product.width <= 0.0 || product.height <= 0.0 || product.length <= 0.0 || product.weight < 0.0){
                    throw OrderException("invalid dimension")
                }
                val distinct = input.items.distinctBy { it.idProduct }.count()
                if (distinct < input.items.size) throw OrderException("duplicated item")
                if (items.quantity <= 0) throw OrderException("invalid quantity")
                val itemFreight = FreightCalculator().calculate(product)
                outPut.freight += itemFreight * items.quantity
                if (product.currency == "USD"){
                    outPut.total += product.price * items.quantity * currencies
                }else{
                    outPut.total += product.price * items.quantity
                }
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
    }
}