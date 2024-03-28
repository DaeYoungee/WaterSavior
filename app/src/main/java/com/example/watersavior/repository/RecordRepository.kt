package com.example.watersavior.repository

import com.example.watersavior.data.ApiState
import com.example.watersavior.data.RequestUpdateWater
import com.example.watersavior.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class RecordRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getWaterCheck(waterRecord: MultipartBody.Part): Flow<ApiState> = flow {

        kotlin.runCatching {
            apiService.getWaterCheck(waterRecord)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun postResultWater(requestUpdateWater: RequestUpdateWater): Flow<ApiState> = flow {

        kotlin.runCatching {
            apiService.postResultWater(requestUpdateWater)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

}