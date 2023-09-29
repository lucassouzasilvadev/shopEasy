package br.com.shopeasy.shopeasy.checkout

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponRepositoryDatabase : CouponRepository, JpaRepository<Coupon, Int> {

    override fun findByCode(code: String): Coupon

}