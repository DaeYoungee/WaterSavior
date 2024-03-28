package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class RequestDatesWater(
    @SerializedName("userId")
    val userId: Int = 1,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
)
