package br.com.shopeasy.shopeasy.checkout

class FreightCalculator {
    fun calculate(product: Product): Double {
        val volume = product.height/100 * product.width/100 * product.length/100
        val density = product.weight/volume
        var itemFreight = 1000 * volume * (density/ 100.0)
        if (itemFreight < 10.0) {
            itemFreight = 10.0
        }
        return itemFreight
    }
}