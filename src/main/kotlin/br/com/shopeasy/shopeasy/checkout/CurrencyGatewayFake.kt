package br.com.shopeasy.shopeasy.checkout

class CurrencyGatewayFake : CurrencyGateway{
    override fun getCurrencies(): Double {
        return 4.0
    }
}