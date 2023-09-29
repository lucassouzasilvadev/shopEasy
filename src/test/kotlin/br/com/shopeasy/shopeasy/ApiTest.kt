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
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTest (@LocalServerPort private val port: Int){

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
    fun`deve criar um pedido com 3 produtos`(){
        given()
            .body(jsonPedidoValido)
                .contentType(ContentType.JSON)
                .accept("*/*")
            .`when`()
                .post()
            .then()
                .body("total", equalTo(19000.0f))
    }

    @Test
    fun `deve criar Um pedido vazio com cpf valido`(){
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
    fun `deve criar um pedido com cupom de desconto`(){
        given()
            .body(jsonPedidoValidoComCoupon)
            .contentType(ContentType.JSON)
            .accept("*/*")
            .`when`()
            .post()
            .then()
            .body("total", equalTo(15200.0f))
    }

    @Test
    fun `nao deve criar um pedido com cpf invalido`(){
        given()
            .body(jsonPedidoComCpfInvalido)
            .contentType(ContentType.JSON)
            .accept("*/*")
            .`when`()
            .post()
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
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
            .then()
            .body("total", equalTo(19000.0f))
    }

    @Test
    fun `nao deve criar um pedido com quantidade negativa`(){
        given()
            .body(jsonPedidoNegativo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("invalid quantity"))
    }

    @Test
    fun `nao deve criar pedido com item duplicado`(){
        given()
            .body(jsonItemDuplicado)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then().body("message", equalTo("duplicated item"))
    }

    @Test
    fun `nao deve criar pedido com dimensao negativa`(){
        given()
            .body(jsonDimensaoNegativa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .body("message", equalTo("invalid dimension"))
    }

    @Test
    fun `deve criar um pedido e calcular o frete`(){
        given()
            .body(jsonCalcularFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .body("freight", equalTo(30.0f))
    }

    @Test
    fun `deve criar um pedido e calcular o frete minimo`(){
        given()
            .body(jsonFreteMinimo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .body("freight", equalTo(10.0f))
    }
}