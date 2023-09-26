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

    @BeforeEach
    fun setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.port = port
        RestAssured.basePath = "/order"

        jsonPedidoValido = ResourceUtils.getContentFromResource("/json/input/jsonPedidoValido.json")
        jsonPedidoValidoComCoupon = ResourceUtils.getContentFromResource("/json/input/jsonPedidoValidoComCoupon.json")
        jsonPedidoComCpfInvalido = ResourceUtils.getContentFromResource("/json/input/jsonPedidoComCpfInvalido.json")
        jsonPedidoVazio = ResourceUtils.getContentFromResource("/json/input/jsonPedidoVazio.json")



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
                .body("total", equalTo(7120.0f))
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
            .body("total", equalTo(0))
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
            .body("total", equalTo(6408.0f))
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
}