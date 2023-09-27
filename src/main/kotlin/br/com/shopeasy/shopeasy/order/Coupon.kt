package br.com.shopeasy.shopeasy.order

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "coupon")
data class Coupon(
    @Id
    var id: Int,
    var code: String,
    var percentage: Double,
    var validate: Int
)
