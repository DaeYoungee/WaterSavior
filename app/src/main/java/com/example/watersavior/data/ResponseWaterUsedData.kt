package com.example.watersavior.data

import com.google.gson.annotations.SerializedName

data class ResponseWaterUsedData(
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("total_tax")
    val totalTax: Double,
    @SerializedName("total_amount")
    val totalAmount: Double,
    @SerializedName("total_time")
    val totalTime: Int,
    @SerializedName("water_tax_list")
    val waterTaxList: DayWater
)
