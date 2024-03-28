package com.example.watersavior.network

import com.example.watersavior.data.DuringDateWater
import com.example.watersavior.data.MonthWater
import com.example.watersavior.data.RequestDatesWater
import com.example.watersavior.data.RequestMonthWater
import com.example.watersavior.data.RequestUpdateWater
import com.example.watersavior.data.WaterSoundResult
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/sound/classify")
    suspend fun getWaterCheck(
        @Part file: MultipartBody.Part,
    ): WaterSoundResult

    @POST("/stats")
    // currentMonth가 바뀔 때 마다 호출, 처음 StatisticScreen 들어갈 때 호출
    suspend fun getMonthWaterBill(
        @Body requestMonthWater: RequestMonthWater
    ): MonthWater

    @POST("/stats/during")
    // check 버튼 눌렀을 때 호출,
    suspend fun getDatesWaterBill(
        @Body requestDatesWater: RequestDatesWater
    ): DuringDateWater

    @POST("/stats/update")
    suspend fun postResultWater(
        @Body requestUpdateWater: RequestUpdateWater
    ): String

}