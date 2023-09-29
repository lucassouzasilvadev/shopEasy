package br.com.shopeasy.shopeasy.checkout

interface CouponRepository {

    fun findByCode(code: String): Coupon

}