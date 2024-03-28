package com.example.watersavior.component.calendar


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.viewmodel.StatisticesViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watersavior.TAG
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

private val primaryColor = Color(0xFF007AFF).copy(alpha = 0.9f)
private val selectionColor = primaryColor
private val continuousSelectionColor = primaryColor.copy(alpha = 0.1f)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(adjacentMonths: Long = 500, viewModel: StatisticesViewModel = viewModel()) {

    val state = rememberCalendarState(
        startMonth = viewModel.startMonth,
        endMonth = viewModel.endMonth,
        firstVisibleMonth = viewModel.currentMonth,
        firstDayOfWeek = viewModel.daysOfWeek.first(),
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)


    val simpleDateTime = viewModel.selection.startDate?.format(
        DateTimeFormatter.ofPattern("dd"))

    Log.d("daeYoung", "selection: ${viewModel.selection}, startDate: $simpleDateTime")

    LaunchedEffect(key1 = state.firstVisibleMonth) {
        Log.d(TAG, "LaunchedEffect() 호출")
        viewModel.getMonthWaterBill(state.firstVisibleMonth.yearMonth)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE1E2E9)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
        ) {

//            Log.d(
//                "daeYoung",
//                "visibleMonth: $visibleMonth\nvisibleMonth.yearMonth: ${visibleMonth.yearMonth}",
//            )
            SimpleCalendarTitle(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                currentMonth = visibleMonth.yearMonth, // ex) 2024-01, currentMonth.displayText() 하면 String 타입, "January 2024"로 반환
                goToPrevious = {
                    coroutineScope.launch {
                        state.firstVisibleMonth.yearMonth.previousMonth.also {
                            state.animateScrollToMonth(it)
                        }
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.firstVisibleMonth.yearMonth.nextMonth.also {
                            state.animateScrollToMonth(it)
                        }
                    }
                },
            )
            Divider(
                thickness = 1.dp,
                color = Color(0xFFD9D9D9),
                modifier = Modifier.padding(bottom = 8.dp),
            )
            HorizontalCalendar(
                modifier = Modifier
                    .testTag("Calendar")
                    .padding(8.dp),
                state = state,
                dayContent = { day ->
                    if (viewModel.dayWater.value.isNotEmpty()) {
                        Day(
                            day,
                            today = viewModel.today,
                            selection = viewModel.selection,
                        ) { day ->
                            if (day.position == DayPosition.MonthDate &&
                                (day.date == viewModel.today || day.date.isBefore(viewModel.today))
                            ) {
                                viewModel.selection = ContinuousSelectionHelper.getSelection(
                                    clickedDate = day.date,
                                    dateSelection = viewModel.selection,
                                )
                            }
                        }
                        Text(
                            text = viewModel.checkDate(day.date.toString()),
                            color = viewModel.setExpensesColor(dayPosition = day.position),
                            modifier = Modifier.align(Alignment.BottomCenter),
                            fontSize = 10.sp,
                        )
                    }

                },
                monthHeader = {
                    MonthHeader(daysOfWeek = viewModel.daysOfWeek)
                },
            )
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = Color.Transparent

    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                showRipple = false,
                onClick = { onClick(day) },
            )
            .backgroundHighlight(
                day = day,
                today = today,
                selection = selection,
                selectionColor = selectionColor,
                continuousSelectionColor = continuousSelectionColor,
            ) { textColor = it },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun Example1Preview() {
    Calendar()
}