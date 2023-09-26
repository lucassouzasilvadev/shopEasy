package br.com.shopeasy.shopeasy.order

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {
}

interface CouponRepository : JpaRepository<Coupon, Int>{
    fun findByCode(code: String): Coupon

}