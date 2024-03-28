package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class DateWater(
    @SerializedName("date")
    val date: String,
    @SerializedName("tax")
    val tax: Double,
)
