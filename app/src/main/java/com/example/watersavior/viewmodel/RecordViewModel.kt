package com.example.watersavior.viewmodel

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.media.MediaRecorder
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watersavior.data.ApiState
import com.example.watersavior.data.RequestUpdateWater
import com.example.watersavior.data.WaterSoundResult
import com.example.watersavior.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.log10
import kotlin.math.round

@HiltViewModel
class RecordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recordRepository: RecordRepository,
) : ViewModel() {

    private val _recoding = mutableStateOf(false)               // 녹음중 인지 상태
    val recoding: State<Boolean> = _recoding

    private val _isWater = mutableStateOf(false)                // 물 소리가 맞는지
    val isWater: State<Boolean> = _isWater

    private val _isResult = mutableStateOf(false)                // 결과 스크린으로 이동
    val isResult: State<Boolean> = _isResult

    private val _db = mutableStateOf("00")                      // 데시벨
    val db: State<String> = _db

    private val _waterPercent = mutableStateOf(0.0)             // 수도 사용량(% 단위)
    val waterPercent: State<Double> = _waterPercent

    private val _formattedTime = mutableStateOf("00 : 00 : 00") // 사용 시간
    val formattedTime: State<String> = _formattedTime

    private val recordPermission = android.Manifest.permission.RECORD_AUDIO
    private var recorder: MediaRecorder? = null
    private lateinit var audioFileName: String
    private var multipart: MultipartBody.Part? = null
    private lateinit var job1: Job                               // Water percent 측정
    private lateinit var job2: Job                               // Decibel 측정
    private lateinit var job3: Job                               // Time 측정
    val snackbarHostState = SnackbarHostState()
    var milliseconds: Long = 0L                          // 사용 시간
    var waterQuantity: Double = 0.0                              // 수도 사용량(m^3 단위)
    var waterBill: Double = 0.0                                  // 수도세
    var dayWaterUsedTime: Long = 0L                              // 1일 누적 수도 사용시간(밀리초)
    var dayWaterQuantity: Double = 0.0                           // 1일 누적 수도 사용량(m^3 단위)
    var dayWaterBill: Double = 0.0                               // 1일 누적 수도세
    private val secondWaterQuantity: Double = 0.000445           // 1초당 수도 사용량, m^3단위
    private val dayAverageWaterQuantity: Double = 0.306          // 2022년도 기준 1인당 일평균 수도 사용량, m^3단위

    private fun changeRecoding() {
        _recoding.value = !_recoding.value
    }

    fun changeIsResult() {
        _isResult.value = !_isResult.value
    }

    // 마이크 퍼미션 설정
    fun checkAudioPermission(activity: Activity): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context,
                recordPermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(recordPermission), 0)
            false
        }
    }

    // 물소리 측정 전체 로직 메서드 녹음 시작 -> 저장 -> 녹음 종료 -> 물 소리 API 통신
    suspend fun checkRecoding() {
        startRecoding()

        viewModelScope.launch(Dispatchers.IO) {  // 코루틴으로 처리하지 않으면 delay() 부터 코드 실행이 안됨
            delay(5000)
            stopRecording()

            waterCheck()
        }
    }


    fun startRecoding() {
        val recordPath = context.getExternalFilesDir("/")!!.absolutePath
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        audioFileName = recordPath + "/" + "RecordExample_" + timeStamp + "_" + "audio.wav";
        recorder = MediaRecorder().apply {
            setAudioSource((MediaRecorder.AudioSource.MIC))     // 오디오 소스 설정, 대부분 MIC 사용
            setOutputFormat((MediaRecorder.OutputFormat.MPEG_4))// 출력 파일 포맷 설정
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)     // 인코더 설정
            setOutputFile(audioFileName)                        // 출력 파일 이름 설정

            try {
                prepare()                                       // MediaRecorder 초기화 완료
            } catch (e: IOException) {
                Log.d("daeYoung", "prepare() failed")
            }

            start()                                             // 녹음 시작
            Log.d("daeYoung", "recordFileName: $audioFileName")
        }
        changeRecoding() // 녹음중으로 바꿔, 그래야만 타이머, 데시벨, 수도사용량 측정 가능해
    }


    fun stopRecording() {
        recorder?.apply {
            stop()                                              // 녹음 중지
            release()                                           // 작업 완료 시 리소스 확보
        }
        recorder = null
        changeRecoding() // 녹음중으로 바꿔, 그래야만 타이머, 데시벨, 수도사용량 측정 가능해
        val file = File(audioFileName)
        val requestBody = file.asRequestBody("audio/mp3".toMediaTypeOrNull())
        multipart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestBody
        )
        Log.d("daeYoung", "fileName: ${file.name} filePath: ${file.absolutePath}")
        Log.d("daeYoung", "requestBody: ${requestBody} content-type: ${requestBody.contentType()}")
        Log.d(
            "daeYoung",
            "multipart: ${multipart} body: ${multipart!!.body} body-content-type: ${multipart!!.body.contentType()} header: ${multipart!!.headers}"
        )
    }

    // 데시벨 측정
    private fun meterDb() {
        recorder?.let {
            job1 = viewModelScope.launch(Dispatchers.IO) {
                while (_recoding.value) {
                    delay(1000)
                    val amplitude = it.maxAmplitude // 진폭 측정
                    if (amplitude > 0) {
                        //진폭이 0 보다 크면 .. toDoSomething
                        //진폭이 0이하이면 데시벨이 -무한대로 나옵니다.
                        val doubleDb = 20 * log10(amplitude.toDouble()) // from 진폭 to 데시벨
                        _db.value =
                            round(doubleDb).toString() // double타입 데시벨을 반올림해서 String으로 타입 캐스팅
                        Log.d("daeYoung", "decibel: $db")
                    } else {
                        _db.value = "00"
                    }
                }
            }
        }
    }

    // 데시벨, 수도량, 타임 비동기 처리 모두 취소
    fun cancelDbAndWaterAndTime() {
        job1.cancel()
        job2.cancel()
        job3.cancel()
        _db.value = "00"
        _formattedTime.value = "00 : 00 : 00"
        _waterPercent.value = 0.0
//        milliseconds = 0L
//        waterQuantity = 0.0
        calcWaterBill()
    }

    // 수도량 측정
    private fun useWater() {
        job2 = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                waterQuantity += secondWaterQuantity                                    // 한번 측정할 때 수도 사용량
                val addPecent =
                    (secondWaterQuantity / dayAverageWaterQuantity) * 100          // 1초당 증가되면 waterPacent의 양
                if (_waterPercent.value.toInt() < 100) {
                    val temp = (_waterPercent.value + addPecent)
                    _waterPercent.value = round(temp * 100) / 100
                }
            }
        }
    }

    // 물 소리가 맞는지 API 통신 메서드

    private suspend fun waterCheck() {

        multipart?.let {
            when (val apiState = recordRepository.getWaterCheck(waterRecord = it).first()) {
                is ApiState.Success<*> -> {
                    val result = apiState.value as WaterSoundResult
                    Log.d("daeYoung", "성공: ${result.result}")
                    if (result.result == 0) {
                        showSnackbar()   // 스낵바 보여짐
                        startRecoding()  // 다시 녹음 상태로 바꿔(데시벨을 측정하기 위함)
                        startTime()      // 타이머 시작
                        meterDb()        // 데시벨 측정 시작
                        useWater()       // 수도 사용량 측정 시작
                        _isWater.value = true
                    } else {
                        _isWater.value = false
                    }
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "실패: ${apiState.errMsg}")
                }

                is ApiState.Loading -> {}
            }
        }

    }

    // 시간 측정

    private fun startTime() {
        job3 = viewModelScope.launch {
            while (true) {
                delay(1000)
                milliseconds += 1000L
                _formattedTime.value = formatTime(milliseconds)
//                Log.d("daeYoung", "time: ${_formattedTime.value}")
            }
        }
    }

    // 시간 format 설정
    private fun formatTime(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
        val formatter = String.format("%02d : %02d : %02d", hours, minutes, seconds)
        return formatter
    }

    fun formatWaterBill(): String {
        val format = DecimalFormat("#,###")
        return format.format(waterBill)
    }

    fun formatWaterQuantity(): String {
        val format = DecimalFormat("#.###")
        return format.format(waterQuantity)
    }

    fun formatUsedTime(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds)
    }

    // 스낵바 UI에 띄움
    private fun showSnackbar() {
        viewModelScope.launch(Dispatchers.IO) {
            snackbarHostState.showSnackbar("Water Sound Ok", "확인", false, SnackbarDuration.Short)
                .let {
                    when (it) {
                        // 스낵바가 시간이 지나 사라질 때 로직처리 하는 부분
                        SnackbarResult.Dismissed ->
                            Log.d("daeYoung", "snackBar: 시간 초과 스낵바 닫아짐")

                        // 스낵바에 있는 버튼이 눌러졌을 때 로직처리 하는 부분
                        SnackbarResult.ActionPerformed ->
                            Log.d("daeYoung", "snackBar: 확인 버튼 눌러짐")
                    }
                }
        }
    }

    private fun calcWaterBill() {
        val secondWaterBill = 0.2581  // 1초 당 0.2581원의 수도세
        val second = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        waterBill = second * secondWaterBill
    }

    fun changeWaterCheckScreen() {
        _isWater.value = false
    }

    suspend fun storeResultWater() {
        val requestUpdateWater = RequestUpdateWater(date = LocalDate.now().toString(), time = milliseconds / 1000, amount = waterQuantity, tax = waterBill)
        when (val apiState =
            recordRepository.postResultWater(requestUpdateWater = requestUpdateWater).first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as String
                Log.d("daeYoung", "성공: ${result}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "실패: ${apiState.errMsg}")
            }

            is ApiState.Loading -> {}
        }
    }

}

