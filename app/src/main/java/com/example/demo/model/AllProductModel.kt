package com.example.demo.model

data class AllProductModel(
    val id: Int,
    val name: String,
    val productList: ArrayList<ProductModel> = arrayListOf()
)