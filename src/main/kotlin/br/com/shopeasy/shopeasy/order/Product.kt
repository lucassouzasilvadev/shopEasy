package br.com.shopeasy.shopeasy.order

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product")
data class Product(
    @Id
    var id_product: Int,
    var description: String,
    var price: Double,
    var altura: Double,
    var largura: Double,
    var profundidade: Double,
    var peso: Double
)
