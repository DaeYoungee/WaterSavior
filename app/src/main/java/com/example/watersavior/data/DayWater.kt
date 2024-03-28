package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class DayWater(
    @SerializedName("date")
    val date: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("time")
    val time: Int,
    @SerializedName("tax")
    val tax: Double,
)