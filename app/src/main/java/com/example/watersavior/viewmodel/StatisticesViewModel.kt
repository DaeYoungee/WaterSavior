package com.example.watersavior.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.watersavior.TAG
import com.example.watersavior.component.calendar.DateSelection
import com.example.watersavior.data.ApiState
import com.example.watersavior.data.DateWater
import com.example.watersavior.data.DayWater
import com.example.watersavior.data.DuringDateWater
import com.example.watersavior.data.MonthWater
import com.example.watersavior.data.RequestDatesWater
import com.example.watersavior.data.RequestMonthWater
import com.example.watersavior.repository.StatisticesRepository
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class StatisticesViewModel @Inject constructor(private val repository: StatisticesRepository) :
    ViewModel() {
    val adjacentMonths = 500L
    var currentMonth = YearMonth.now()
    val startMonth = currentMonth.minusMonths(adjacentMonths)
    val endMonth = currentMonth.plusMonths(adjacentMonths)
    val today = LocalDate.now()
    val daysOfWeek = daysOfWeek()


    private val _day = mutableStateOf(Day("", ""))
    val day: State<Day> = _day

    var selection by mutableStateOf(DateSelection())

    private val _monthWater = mutableStateOf(
        MonthWater(
            month = currentMonth.toString(),
            totalTax = 0.0,
            totalAmount = 0.0,
            totalTime = 0,
            waterUsedList = emptyList()
        )
    )
    val monthWater: State<MonthWater> = _monthWater

    private val _dayWater = mutableStateOf(emptyList<DateWater>())
    val dayWater: State<List<DateWater>> = _dayWater

    private val modelProducer = CartesianChartModelProducer.build()

    fun setList(x:List<Double>, y: List<Double>) {
        if (y.isNotEmpty()) {
            modelProducer.tryRunTransaction {
                lineSeries {
                    series(x = x,y = y)
                }
            }
        }
    }

    fun getModelProducer() = modelProducer

    fun reset() {
        _day.value = Day("", "")
        selection = DateSelection()
    }

    val setStartDay: (String) -> Unit = { text ->
        if (_day.value.startDay.length < 2) {
            _day.value = Day(startDay = text, endDay = _day.value.endDay)
//            Log.d("daeYoung", "text: $text day: ${day.value}")
        } else if (_day.value.startDay.length >= text.length) {   // 십의 자리 까지 입력한 뒤 지우기 버튼 이슈 해결
            _day.value = Day(startDay = text, endDay = _day.value.endDay)
        }
    }

    val setEndDay: (String) -> Unit = { text ->
        if (_day.value.startDay.isNotEmpty() && _day.value.endDay.length < 2) {
            _day.value = Day(startDay = _day.value.startDay, endDay = text)
        } else if (_day.value.endDay.length > text.length) {
            _day.value = Day(startDay = _day.value.startDay, endDay = text)
        }
    }

    fun legendMonth(): String =
        currentMonth.format(
            DateTimeFormatter.ofPattern("MM")
        )

    val formatDate: (LocalDate) -> String = { localDate ->
        localDate.format(
            DateTimeFormatter.ofPattern("dd")
        )
    }

    val formatMonth: (YearMonth) -> String = { yearMonth ->
        yearMonth.format(
            DateTimeFormatter.ofPattern("MM")
        )
    }

    fun bottomAxisValue(): List<Double> {
        val dateList = mutableListOf<Double>()
        _dayWater.value.forEach {
            val data = it.date.split("-")[2].toDouble()
            dateList.add(data)
        }
        return dateList
    }

    fun setExpensesColor(dayPosition: DayPosition) = when (dayPosition) {
        DayPosition.MonthDate -> {
            Color(0xFFFF0000)
        }

        DayPosition.InDate -> {
            Color.Transparent
        }

        DayPosition.OutDate -> {
            Color.Transparent
        }
    }

    fun checkEnabled() = _day.value.endDay.isNotEmpty()

    suspend fun getMonthWaterBill(month: YearMonth) {
        currentMonth = month
        val requestMonthWater = RequestMonthWater(userId = 1, month = month.toString())
        when (val apiState = repository.getMonthWaterBill(requestMonthWater).first()) {
            is ApiState.Success<*> -> {
                _monthWater.value = apiState.value as MonthWater
                _dayWater.value =
                    _monthWater.value.waterUsedList.map { DateWater(date = it.date, tax = it.tax) }
                val yList =
                    _monthWater.value.waterUsedList.map { it.tax }.map { ceil(it) }
                val xList = bottomAxisValue()
                setList(x = xList, y = yList)
                Log.d(TAG, "result: ${_dayWater.value}\n xList: $xList\n yList: $yList")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> {}
        }
    }

    fun checkDate(date: String): String {
        val bill = _dayWater.value.find { it.date == date }?.tax
        return bill?.toString() ?: ""
    }

    suspend fun getDatesWaterBill() {
        val requestDatesWater = RequestDatesWater(
            startDate = selection.startDate.toString(),
            endDate = selection.endDate.toString()
        )
        when (val apiState = repository.getDatesWaterBill(requestDatesWater).first()) {
            is ApiState.Success<*> -> {
                _dayWater.value = (apiState.value as DuringDateWater).waterTaxList
                val duringDatesList = dayWater.value.map { it.tax }
                val xList = bottomAxisValue()

                setList(x = xList, y = duringDatesList)

                Log.d(TAG, "result: ${_dayWater.value}\n")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> {}
        }
    }
}

data class Day(val startDay: String, val endDay: String)