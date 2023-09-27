package br.com.shopeasy.shopeasy

import br.com.shopeasy.shopeasy.util.ResourceUtils
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderTest (@LocalServerPort private val port: Int){

    private var jsonPedidoValido: String = ""
    private var jsonPedidoValidoComCoupon: String = ""
    private var jsonPedidoComCpfInvalido: String = ""
    private var jsonPedidoVazio: String = ""
    private var jsonCupomExpirado: String = ""
    private var jsonPedidoNegativo: String = ""
    private var jsonItemDuplicado: String = ""
    private var jsonPesoNegativo: String = ""
    private var jsonDimensaoNegativa: String = ""
    private var jsonCalcularFrete: String = ""
    private var jsonFreteMinimo: String = ""





    @BeforeEach
    fun setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.port = port
        RestAssured.basePath = "/order"

        jsonPedidoValido = ResourceUtils.getContentFromResource("/json/input/jsonPedidoValido.json")
        jsonPedidoValidoComCoupon = ResourceUtils.getContentFromResource("/json/input/jsonPedidoValidoComCupom.json")
        jsonPedidoComCpfInvalido = ResourceUtils.getContentFromResource("/json/input/jsonPedidoComCpfInvalido.json")
        jsonPedidoVazio = ResourceUtils.getContentFromResource("/json/input/jsonPedidoVazio.json")
        jsonCupomExpirado = ResourceUtils.getContentFromResource("/json/input/jsonCupomExpirado.json")
        jsonPedidoNegativo = ResourceUtils.getContentFromResource("/json/input/jsonPedidoNegativo.json")
        jsonItemDuplicado = ResourceUtils.getContentFromResource("/json/input/jsonItemDuplicado.json")
        jsonDimensaoNegativa = ResourceUtils.getContentFromResource("/json/input/jsonDimensaoNegativa.json")
        jsonPesoNegativo = ResourceUtils.getContentFromResource("/json/input/jsonPesoNegativo.json")
        jsonCalcularFrete = ResourceUtils.getContentFromResource("/json/input/jsonCalcularFrete.json")
        jsonFreteMinimo = ResourceUtils.getContentFromResource("/json/input/jsonFreteMinimo.json")


    }

    @Test
    fun deveCriarUmPedidoCom3Produtos(){
        given()
            .body(jsonPedidoValido)
                .contentType(ContentType.JSON)
                .accept("*/*")
            .`when`()
                .post()
            .then()
                .body("total", equalTo(7560.0f))
    }

    @Test
    fun deveCriarUmPedidoVazioComCpfValido(){
        given()
            .body(jsonPedidoVazio)
            .contentType(ContentType.JSON)
            .accept("*/*")
            .`when`()
            .post()
            .then()
            .body("total", equalTo(0.0f))
    }

    @Test
    fun deveCriarUmPedidoComCupomDeDesconto(){
        given()
            .body(jsonPedidoValidoComCoupon)
            .contentType(ContentType.JSON)
            .accept("*/*")
            .`when`()
            .post()
            .then()
            .body("total", equalTo(6048.0f))
    }

    @Test
    fun naoDeveCriarUmPedidoComCpfInvalido(){
        given()
            .body(jsonPedidoComCpfInvalido)
            .contentType(ContentType.JSON)
            .accept("*/*")
            .`when`()
            .post()
            .then()
            .body("message",equalTo("cpf is invalid"))
    }

    @Test
    fun `nao deve aplicar um cupom de desconto expirado`(){
        given()
            .body(jsonCupomExpirado)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("cupom está expirado"))
    }

    @Test
    fun `pedido nao pode ter item com quantidade negativa`(){
        given()
            .body(jsonPedidoNegativo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("pedido não pode ter quantidade negativa"))
    }

    @Test
    fun `item do pedido nao pode ser informado mais de uma vez`(){
        given()
            .body(jsonItemDuplicado)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("existe algum item duplicado na lista"))
    }

    @Test
    fun `pedido nao pode ter items com dimensao negativa`(){
        given()
            .body(jsonDimensaoNegativa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("Nenhuma dimensão do item pode ser negativa"))
    }

    @Test
    fun ` pedido nao pode ter items com peso negativo`(){
        given()
            .body(jsonPesoNegativo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("O peso do item não pode ser negativo"))
    }

    @Test
    fun ` deve calcular o frete do pedido `(){
        given()
            .body(jsonCalcularFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .body("frete", equalTo(30.0f))
    }

    @Test
    fun ` deve calcular o frete minimo `(){
        given()
            .body(jsonFreteMinimo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .body("frete", equalTo(10.0f))
    }
}