package com.example.watersavior.data

// request
data class WaterResult(
    val usedId: Int,
    val date: String,
    val amount: Int,
    val time: Int,
    val tax: Int
)
