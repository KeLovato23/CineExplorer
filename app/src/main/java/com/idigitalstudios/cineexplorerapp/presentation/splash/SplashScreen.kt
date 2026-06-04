package com.idigitalstudios.cineexplorerapp.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.idigitalstudios.cineexplorerapp.ui.theme.CineRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val scaleAnim = remember { Animatable(1.35f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Aparece desde grande hacia tamaño normal (zoom-in de enfoque)
        launch { scaleAnim.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }
        launch { alphaAnim.animateTo(1f, tween(450)) }
        delay(700)
        // Slow cinematic push-in estilo Cinemax
        scaleAnim.animateTo(1.1f, tween(1400, easing = LinearEasing))
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) { append("Cine") }
                withStyle(SpanStyle(color = CineRed)) { append("Explorer") }
            },
            fontSize = 44.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 3.sp,
            modifier = Modifier
                .scale(scaleAnim.value)
                .alpha(alphaAnim.value)
        )
    }
}
