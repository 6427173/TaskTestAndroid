package com.example.demo.model

data class ProductModel(
    val id: Int,
    val name: String,
    val price: String,
    val productImage: Int,
    var type: ProductType = ProductType.Category
)