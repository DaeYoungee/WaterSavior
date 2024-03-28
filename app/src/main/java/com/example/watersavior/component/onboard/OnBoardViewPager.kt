package com.example.watersavior.component.onboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.R
import com.example.watersavior.component.common.BlueButton
import com.example.watersavior.component.common.WhiteButton
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.SpringIndicatorType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardViewPager(modifier: Modifier = Modifier) {
    val pageCount = Int.MAX_VALUE

    val viewPagerItems =
        listOf(ViewPagerItem.Item1, ViewPagerItem.Item2, ViewPagerItem.Item3, ViewPagerItem.Item4)

    val horizontalState = rememberPagerState { pageCount }
    val verticalState = rememberPagerState { pageCount }
    val contentPadding = PaddingValues(top = 50.dp, start = 70.dp, end = 70.dp)
    val indicatorType = SpringIndicatorType(
        dotsGraphic = DotGraphic(
            13.dp,
            borderWidth = 2.dp,
            borderColor = colorResource(id = R.color.main_blue),
            color = Color.Transparent
        ),
        selectorDotGraphic = DotGraphic(
            11.dp,
            color = colorResource(id = R.color.main_blue)
        )
    )

    LaunchedEffect(Unit) {
        snapshotFlow {
            Pair(
                horizontalState.currentPage,
                horizontalState.currentPageOffsetFraction
            )
        }.collect { (page, offset) ->
            verticalState.scrollToPage(page, offset)
        }
    }
    HorizontalPager(
        state = horizontalState,
        modifier = modifier
            .fillMaxHeight(0.5f),
        pageSpacing = 20.dp,
        contentPadding = contentPadding,
    ) { page ->
//        Log.d(TAG, "page: ${horizontalState.currentPage}\nfraction: ${horizontalState.currentPageOffsetFraction}")
        val realPage = page % viewPagerItems.size
        ViewPagerCard(
            page = page,
            realPage = realPage,
            viewPagerItems = viewPagerItems,
            horizontalState = horizontalState
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    DotsIndicator(
        dotCount = viewPagerItems.size,
        type = indicatorType,
        currentPage = horizontalState.currentPage % viewPagerItems.size,
        currentPageOffsetFraction = {
            if (horizontalState.currentPage % 4 == 0 && horizontalState.currentPageOffsetFraction <= 0f) {
                0f
            } else {
                horizontalState.currentPageOffsetFraction
            }
        })
    Spacer(modifier = Modifier.height(20.dp))
    VerticalPager(
        state = verticalState,
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .padding(horizontal = 70.dp),
        userScrollEnabled = false,
    ) { page ->
        val realPage = page % viewPagerItems.size
        Text(
            text = "${viewPagerItems[realPage].explain}",
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Test() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "저희 어플리케이션의 다양한 기능을 소개합니다!",
            modifier = Modifier.padding(horizontal = 70.dp),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
        OnBoardViewPager(modifier = Modifier)
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(120.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            BlueButton(text = "Sign up") {}
            WhiteButton(text = "Sign in") {}
        }
    }
}