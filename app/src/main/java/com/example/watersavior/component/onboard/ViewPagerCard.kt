package com.example.watersavior.component.onboard

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.TAG
import com.google.android.material.animation.AnimationUtils
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScope.ViewPagerCard(
    page: Int,
    realPage: Int,
    viewPagerItems: List<ViewPagerItem>,
    horizontalState: PagerState
) {

    Card(
        Modifier
            .graphicsLayer {
                val pageOffset = abs(horizontalState.getOffsetFractionForPage(page))
                AnimationUtils
                    .lerp(
                        0.85f,
                        1f,
                        1f - pageOffset
                    )
                    .also { scale ->
                        this.scaleX = scale
                        this.scaleY = scale
//                        Log.d(TAG, "page: ${page}\nfraction: ${pageOffset}\n scale: $scale\n translationX: $translationX")
                    }

                // We animate the alpha, between 50% and 100%
                this.alpha = AnimationUtils.lerp(
                    0.5f,
                    1f,
                    1f - pageOffset.coerceIn(0f, 1f)
                )
            },
        shape = RoundedCornerShape(size = 20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = viewPagerItems[realPage].image),
                contentDescription = "",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = viewPagerItems[realPage].title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
    }
}