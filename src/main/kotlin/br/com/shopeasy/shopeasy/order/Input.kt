package br.com.shopeasy.shopeasy.order

data class Input(
    var cpf: String,
    var items: List<Products> = mutableListOf(),
    var coupon: String? = "",
    var from: String? = "",
    var to: String? = ""
){
    data class Products(
        var idProduct: Int,
        var quantity: Int,
    )
}
