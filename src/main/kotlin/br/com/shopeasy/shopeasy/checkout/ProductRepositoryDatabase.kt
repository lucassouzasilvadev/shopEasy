package br.com.shopeasy.shopeasy.checkout

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepositoryDatabase : ProductRepository, JpaRepository<Product, Int> {

    @Query("SELECT p FROM Product p WHERE p.id_product = :idProduct")
    override fun findProductById(@Param("idProduct") idProduct: Int): Product


}

