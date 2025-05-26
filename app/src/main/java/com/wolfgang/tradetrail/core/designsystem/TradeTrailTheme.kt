package com.wolfgang.tradetrail.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wolfgang.tradetrail.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

// --- Novas Cores ---
val Gold = Color(0xFFB79A62)
val Red = Color(0xFFD93F3F)
val OffWhite = Color(0xFFF8F8F8)
val Yellow = Color(0xFFFE9D0A)
val DarkGrey = Color(0xFF424242)
val DarkerGrey = Color(0xFF2D2D2D)
val Black = Color(0xFF000000)
val LightGrey = Color(0xFFF5F5F5)
val TextGrey = Color(0xFFA0A0A0)

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = OffWhite,
    primaryContainer = Color(0xFF5E450F),
    onPrimaryContainer = OffWhite,
    secondary = DarkGrey,
    onSecondary = OffWhite,
    secondaryContainer = Color(0xFF333333),
    onSecondaryContainer = OffWhite,
    tertiary = Red,
    onTertiary = OffWhite,
    tertiaryContainer = Color(0xFF6E2020),
    onTertiaryContainer = OffWhite,
    error = Red,
    onError = Black,
    background = DarkerGrey,
    onBackground = OffWhite,
    surface = DarkerGrey,
    onSurface = OffWhite,
    surfaceVariant = DarkGrey,
    onSurfaceVariant = OffWhite,
    outline = DarkGrey
)

private val LightColorScheme = lightColorScheme(
    primary = Gold,
    onPrimary = OffWhite,
    primaryContainer = Color(0xFFF0E5D1),
    onPrimaryContainer = Black,
    secondary = DarkGrey,
    onSecondary = OffWhite,
    secondaryContainer = Color(0xFFE0E0E0),
    onSecondaryContainer = Black,
    tertiary = Red,
    onTertiary = OffWhite,
    tertiaryContainer = Color(0xFFF5C6C6),
    onTertiaryContainer = Black,
    error = Red,
    onError = OffWhite,
    background = LightGrey,
    onBackground = Black,
    surface = OffWhite,
    onSurface = Black,
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Black,
    outline = Color(0xFFCCCCCC)
)

private val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 38.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun TradeTrailTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}