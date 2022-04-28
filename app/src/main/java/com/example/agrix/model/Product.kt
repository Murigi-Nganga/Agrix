package com.example.agrix.model

data class Product(
                   val productName: String,
                   val measurement: String,
                   val quantity: Double,
                   val lastEdited: String,         // Date and Time in a String format
)