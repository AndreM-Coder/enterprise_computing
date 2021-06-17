package com.example.app_compras

data class ProductCart (val id: Int, val imageViewProduct: String, val textViewProductName: String,
                        val textViewProductPrice: String, val textViewProductPriceBefore: String,
                        val categoryProduct: String, val descriptionProduct: String,
                        val quantidade: Int, val stock: String, val promotion: String,
                        val textViewQuantityProductCart: Int)