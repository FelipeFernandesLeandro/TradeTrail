package com.wolfgang.tradetrail.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val LightColorScheme = lightColorScheme(
    primary            = Color(0xFF0057D9),
    onPrimary          = Color(0xFFFFFFFF),
    primaryContainer   = Color(0xFFD4E3FF),
    onPrimaryContainer = Color(0xFF001A41),
    secondary          = Color(0xFFFA6400),
    onSecondary        = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFE1D0),
    onSecondaryContainer = Color(0xFF291200),
    tertiary           = Color(0xFF16B26E),
    onTertiary         = Color(0xFFFFFFFF),
    tertiaryContainer  = Color(0xFFBAF2D5),
    onTertiaryContainer = Color(0xFF00210D),
    error              = Color(0xFFB3261E),
    onError            = Color(0xFFFFFFFF),
    errorContainer     = Color(0xFFF9DEDC),
    onErrorContainer   = Color(0xFF410E0B),
    background         = Color(0xFFFFFFFF),
    onBackground       = Color(0xFF1C1B1F),
    surface            = Color(0xFFFFFFFF),
    onSurface          = Color(0xFF1C1B1F),
    surfaceVariant     = Color(0xFFE1E2EC),
    onSurfaceVariant   = Color(0xFF44464F),
    outline            = Color(0xFF74777F)
)

private val DarkColorScheme = darkColorScheme(
    primary            = Color(0xFFA8C7FF),
    onPrimary          = Color(0xFF00306F),
    primaryContainer   = Color(0xFF00479D),
    onPrimaryContainer = Color(0xFFD4E3FF),
    secondary          = Color(0xFFFFB783),
    onSecondary        = Color(0xFF4A1E00),
    secondaryContainer = Color(0xFF6A3900),
    onSecondaryContainer = Color(0xFFFFE1D0),
    tertiary           = Color(0xFF78D9B0),
    onTertiary         = Color(0xFF003821),
    tertiaryContainer  = Color(0xFF005232),
    onTertiaryContainer = Color(0xFFBAF2D5),
    error              = Color(0xFFF2B8B5),
    onError            = Color(0xFF601410),
    errorContainer     = Color(0xFF8C1D18),
    onErrorContainer   = Color(0xFFF9DEDC),
    background         = Color(0xFF1C1B1F),
    onBackground       = Color(0xFFE6E1E5),
    surface            = Color(0xFF1C1B1F),
    onSurface          = Color(0xFFE6E1E5),
    surfaceVariant     = Color(0xFF44464F),
    onSurfaceVariant   = Color(0xFFC5C6D0),
    outline            = Color(0xFF8E9099)
)

private val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize   = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 20.sp
    )
)

private val AppShapes = Shapes(
    small  = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large  = RoundedCornerShape(0.dp)
)

@Composable
fun TradeTrailTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = AppTypography,
        shapes      = AppShapes,
        content     = content
    )
}
