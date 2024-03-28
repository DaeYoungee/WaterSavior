package com.example.watersavior.component.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.watersavior.R
import com.example.watersavior.component.calendar.ContinuousSelectionHelper.isInDateBetweenSelection
import com.example.watersavior.component.calendar.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.Week
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import java.time.LocalDate

private class HalfSizeShape(private val clipStart: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val half = size.width / 2f
        val offset = if (layoutDirection == LayoutDirection.Ltr) {
            if (clipStart) Offset(half, 0f) else Offset.Zero
        } else {
            if (clipStart) Offset.Zero else Offset(half, 0f)
        }
        return Outline.Rectangle(Rect(offset, Size(half, size.height)))
    }
}

fun Modifier.clickable(
    enabled: Boolean = true,
    showRipple: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = if (showRipple) LocalIndication.current else null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Modifier.backgroundHighlight(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    selectionColor: Color,
    continuousSelectionColor: Color,
    textColor: (Color) -> Unit,
): Modifier = composed {
    val (startDate, endDate) = selection
    val padding = 4.dp
    when (day.position) {
        DayPosition.MonthDate -> {
            when {
                startDate == day.date && endDate == null -> {
                    Log.d("daeYoung", "startDate == day.date && endDate == null 들어왔나? ")
                    Log.d("daeYoung", "day.date: ${day.date}\nstartDate: ${startDate}")
                    textColor(Color.White)
                    padding(padding)
                        .background(color = selectionColor, shape = CircleShape)
                }

                day.date == startDate -> {
                    Log.d("daeYoung", "day.date == startDat 들어왔나? ")
                    Log.d("daeYoung", "day.date: ${day.date}\nstartDate: ${startDate}")
                    textColor(Color.White)
                    padding(vertical = padding)
                        .background(
                            color = continuousSelectionColor,
                            shape = HalfSizeShape(clipStart = true),
                        )
                        .padding(horizontal = padding)
                        .background(color = selectionColor, shape = CircleShape)
                }

                startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                    textColor(colorResource(R.color.example_4_grey))
                    padding(vertical = padding)
                        .background(color = continuousSelectionColor)
                }

                day.date == endDate -> {
                    textColor(Color.White)
                    padding(vertical = padding)
                        .background(
                            color = continuousSelectionColor,
                            shape = HalfSizeShape(clipStart = false),
                        )
                        .padding(horizontal = padding)
                        .background(color = selectionColor, shape = CircleShape)
                }

                day.date.isBefore(today) -> {
//                    textColor(colorResource(R.color.inactive_text_color))
                    textColor(colorResource(R.color.example_4_grey))
                    this
                }


                day.date == today -> {
                    textColor(colorResource(R.color.example_4_grey))
                    padding(padding)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = colorResource(R.color.inactive_text_color),
                        )
                }

                else -> {
//                    textColor(colorResource(R.color.example_4_grey))
                    textColor(colorResource(R.color.inactive_text_color))
                    this
                }
            }
        }

        DayPosition.InDate -> {
            textColor(Color.Transparent)
            if (startDate != null && endDate != null &&
                isInDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
                    .background(color = continuousSelectionColor)
            } else this
        }

        DayPosition.OutDate -> {
            textColor(Color.Transparent)
            if (startDate != null && endDate != null &&
                isOutDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
                    .background(color = continuousSelectionColor)
            } else this
        }
    }
}

//@Composable
//fun StatusBarColorUpdateEffect(color: Color) {
//    if (LocalInspectionMode.current) return // findActivity() will not work in preview.
//    val activity = LocalContext.current.findActivity()
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val observer = remember {
//        StatusBarColorLifecycleObserver(activity, color.toArgb())
//    }
//    LaunchedEffect(lifecycleOwner) {
//        lifecycleOwner.lifecycle.addObserver(observer)
//    }
//}

/**
 * Alternative way to find the first fully visible month in the layout.
 *
 * @see [rememberFirstVisibleMonthAfterScroll]
 * @see [rememberFirstMostVisibleMonth]
 */
@Composable
fun rememberFirstCompletelyVisibleMonth(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    // Only take non-null values as null will be produced when the
    // list is mid-scroll as no index will be completely visible.
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.completelyVisibleMonths.firstOrNull() }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

/**
 * Returns the first visible month in a paged calendar **after** scrolling stops.
 *
 * @see [rememberFirstCompletelyVisibleMonth]
 * @see [rememberFirstMostVisibleMonth]
 */
@Composable
fun rememberFirstVisibleMonthAfterScroll(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleMonth.value = state.firstVisibleMonth }
    }
    return visibleMonth.value
}

/**
 * Find first visible week in a paged week calendar **after** scrolling stops.
 */
//@Composable
//fun rememberFirstVisibleWeekAfterScroll(state: WeekCalendarState): Week {
//    val visibleWeek = remember(state) { mutableStateOf(state.firstVisibleWeek) }
//    LaunchedEffect(state) {
//        snapshotFlow { state.isScrollInProgress }
//            .filter { scrolling -> !scrolling }
//            .collect { visibleWeek.value = state.firstVisibleWeek }
//    }
//    return visibleWeek.value
//}

/**
 * Find the first month on the calendar visible up to the given [viewportPercent] size.
 *
 * @see [rememberFirstCompletelyVisibleMonth]
 * @see [rememberFirstVisibleMonthAfterScroll]
 */
@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private val CalendarLayoutInfo.completelyVisibleMonths: List<CalendarMonth>
    get() {
        val visibleItemsInfo = this.visibleMonthsInfo.toMutableList()
        return if (visibleItemsInfo.isEmpty()) {
            emptyList()
        } else {
            val lastItem = visibleItemsInfo.last()
            val viewportSize = this.viewportEndOffset + this.viewportStartOffset
            if (lastItem.offset + lastItem.size > viewportSize) {
                visibleItemsInfo.removeLast()
            }
            val firstItem = visibleItemsInfo.firstOrNull()
            if (firstItem != null && firstItem.offset < this.viewportStartOffset) {
                visibleItemsInfo.removeFirst()
            }
            visibleItemsInfo.map { it.month }
        }
    }

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}