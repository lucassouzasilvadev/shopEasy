package br.com.shopeasy.shopeasy.order

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
    var height: Double,
    var width: Double,
    var length: Double,
    var weight: Double
)
