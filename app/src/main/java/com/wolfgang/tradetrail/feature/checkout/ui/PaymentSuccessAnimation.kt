package com.wolfgang.tradetrail.feature.checkout.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wolfgang.tradetrail.R

@Composable
fun paymentSuccessAnimation(): Float {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkout))
    val animationState by animateLottieCompositionAsState(
        composition            = composition,
        iterations             = 1,
        speed                  = 1f,
        restartOnPlay          = false
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = composition,
            progress    = { animationState },
            modifier    = Modifier.size(200.dp)
        )
    }

    return animationState
}
