package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class RequestUpdateWater(
    @SerializedName("userId")
    val userId: Int = 1,
    @SerializedName("date")
    val date: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("time")
    val time: Long,
    @SerializedName("tax")
    val tax: Double,
)
