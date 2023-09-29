package br.com.shopeasy.shopeasy.checkout

interface ProductRepository {
    fun findProductById(idProduct: Int): Product
}