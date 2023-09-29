package br.com.shopeasy.shopeasy

import br.com.shopeasy.shopeasy.checkout.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CheckoutTest {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Autowired
    private lateinit var currencyGateway: CurrencyGateway

    @Autowired
    lateinit var checkout: Checkout


    @BeforeEach
    fun setUp(){
        checkout = Checkout(productRepository, couponRepository, currencyGateway)
    }

    @Test
    fun`deve criar um pedido com 3 produtos`(){
        val input = Input(cpf = "429.875.898-24",
                    items = listOf(
                        Input.Products(idProduct = 1, quantity = 2),
                        Input.Products(idProduct = 2, quantity = 1),
                        Input.Products(idProduct = 3, quantity = 4)
                    ))
        val checkout = checkout.execute(input)
        assertEquals( 19000.0, checkout.total)
    }

    @Test
    fun `deve criar Um pedido vazio com cpf valido`(){
        val input = Input(cpf = "429.875.898-24")
        assertEquals(0.0, checkout.execute(input).total)
    }

    @Test
    fun `deve criar um pedido com cupom de desconto`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 1, quantity = 2),
                Input.Products(idProduct = 2, quantity = 1),
                Input.Products(idProduct = 3, quantity = 4)
            ),
            coupon = "VALE20")
        assertEquals(15200.0, checkout.execute(input).total )
    }

    @Test
    fun `nao deve criar um pedido com cpf invalido`(){
        val input = Input(cpf = "429.875.898-25",
            items = listOf(
                Input.Products(idProduct = 1, quantity = 2),
                Input.Products(idProduct = 2, quantity = 1),
                Input.Products(idProduct = 3, quantity = 4)
            ),
            coupon = "VALE20")
        val exception = assertThrows<OrderException>{
            checkout.execute(input)
        }
        assertEquals("cpf is invalid", exception.message)
    }

    @Test
    fun `nao deve aplicar um cupom de desconto expirado`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 1, quantity = 2),
                Input.Products(idProduct = 2, quantity = 1),
                Input.Products(idProduct = 3, quantity = 4)
            ),
            coupon = "VALE10")
        assertEquals(19000.0, checkout.execute(input).total )
    }

    @Test
    fun `nao deve criar um pedido com quantidade negativa`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 1, quantity = -2),
                Input.Products(idProduct = 2, quantity = 1),
                Input.Products(idProduct = 3, quantity = 4)
            ),
            coupon = "VALE20")
        val exception = assertThrows<OrderException> {
            checkout.execute(input)
        }
        assertEquals("invalid quantity", exception.message)
    }

    @Test
    fun `nao deve criar pedido com item duplicado`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 1, quantity = 2),
                Input.Products(idProduct = 1, quantity = 2),
                Input.Products(idProduct = 2, quantity = 1),
                Input.Products(idProduct = 3, quantity = 4)
            ),)
        val exception = assertThrows<OrderException> {
            checkout.execute(input)
        }
        assertEquals("duplicated item", exception.message)
    }

    @Test
    fun `nao deve criar pedido com dimensao negativa`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 4, quantity = 2),
            ),)
        val exception = assertThrows<OrderException> {
            checkout.execute(input)
        }
        assertEquals("invalid dimension", exception.message)
    }


    @Test
    fun `deve criar um pedido e calcular o frete`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 2, quantity = 1),
            ),
            from = "121545",
            to = "1215421")
        assertEquals(30.0, checkout.execute(input).freight )
    }

    @Test
    fun `deve criar um pedido e calcular o frete minimo`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 6, quantity = 1),
            ),
            from = "121545",
            to = "1215421")
        assertEquals(10.0, checkout.execute(input).freight)
    }

    @Test
    fun`deve criar um pedido com 1 produto em dolar usando um stub`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 5, quantity = 1)
            ))
        val stub = mock(CurrencyGatewayHttp::class.java)
        `when`(stub.getCurrencies()).thenReturn(2.0)
        val stubProduct = mock(ProductRepository::class.java)
        `when`(stubProduct.findProductById(5)).thenReturn(
            Product(id_product = 5, description = "vazia", price = 1000.0, height = 10.0, width = 10.0, length = 10.0, weight = 10.0, currency = "USD" )
        )
        val checkout = Checkout(stubProduct, couponRepository, stub)
        val response = checkout.execute(input)
        assertEquals(2000.0, response.total)
    }



    @Test
    fun `deve criar um pedido com cupom de desconto com spy`() {
        val spy = spy(CouponRepositoryDatabase::class.java)
        doReturn(Coupon(id = 1, code = "VALE20", percentage = 20.0, validate = 20230930)).`when`(spy).findByCode("VALE20")
        val checkout = Checkout(productRepository, spy, currencyGateway)
        val input = Input(
            cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 1, quantity = 2),
                Input.Products(idProduct = 2, quantity = 1),
                Input.Products(idProduct = 3, quantity = 4)
            ),
            coupon = "VALE20"
        )
        val executeCheckout = checkout.execute(input)
        verify(spy, times(1)).findByCode("VALE20")
        assertEquals(15200.0, executeCheckout.total)
    }


    @Test
    fun`deve criar um pedido com 1 produto em dolar usando um fake`(){
        val input = Input(cpf = "429.875.898-24",
            items = listOf(
                Input.Products(idProduct = 7, quantity = 1)
            ))
        currencyGateway = CurrencyGatewayFake()
        val checkout = Checkout(productRepository, couponRepository, currencyGateway)
        val response = checkout.execute(input)
        assertEquals(4000.0, response.total)
    }


}