package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class RequestMonthWater(
    @SerializedName("userId")
    val userId: Int = 1,
    @SerializedName("month")
    val month: String,
)
