package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class MonthWater(
    @SerializedName("month")
    val month: String,
    @SerializedName("total_tax")
    val totalTax: Double,
    @SerializedName("total_amount")
    val totalAmount: Double,
    @SerializedName("total_time")
    val totalTime: Int,
    @SerializedName("water_used_list")
    val waterUsedList: List<DayWater>
)
