package com.example.watersavior.repository

import com.example.watersavior.data.ApiState
import com.example.watersavior.data.RequestDatesWater
import com.example.watersavior.data.RequestMonthWater
import com.example.watersavior.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StatisticesRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMonthWaterBill(requestMonthWater: RequestMonthWater):Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getMonthWaterBill(requestMonthWater = requestMonthWater)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDatesWaterBill(requestDatesWater: RequestDatesWater):Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getDatesWaterBill(requestDatesWater = requestDatesWater)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}