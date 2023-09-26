package br.com.shopeasy.shopeasy

import br.com.shopeasy.shopeasy.validate.ValidateCpf
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidateCpfTest {

    @Test
    fun deveTestarUmCpfValido(){
       val isValid = ValidateCpf("429.875.898-24").isValidCpf()
       assertEquals(isValid, true)
    }


    @ParameterizedTest
    @ValueSource(strings = ["429.875.898-25", "429.875.898", " "])
    fun deveTestarUmCpfInvalido(cpf: String){
        val isValid = ValidateCpf(cpf).isValidCpf()
        assertEquals(isValid, false)
    }

    @ParameterizedTest
    @ValueSource(strings = ["444.444.444-44", "333.333.333-33"])
    fun deveTestarUmCpfInvalidoComTodosOsDigitosIguais(cpf: String){
        val isValid = ValidateCpf(cpf).isValidCpf()
        assertEquals(isValid, false)
    }


}