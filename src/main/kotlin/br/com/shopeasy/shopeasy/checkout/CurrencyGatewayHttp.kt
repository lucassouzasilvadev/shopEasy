package br.com.shopeasy.shopeasy.checkout

import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class CurrencyGatewayHttp : CurrencyGateway{

    override fun getCurrencies(): Double {
        val cliente = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/currency"))
            .build()

        return cliente.send(request, HttpResponse.BodyHandlers.ofString()).body().toDouble()
    }

}