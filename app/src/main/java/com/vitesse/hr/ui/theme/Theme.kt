package com.vitesse.hr.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFDF9780),
    secondary = Color(0xFFE8B952),
    //  primary = Purple80,
    // secondary = PurpleGrey80,
    // tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFDF9780),
    secondary = Color(0xFFE8B952),
    //   tertiary = Color(0xFF749FB8),

    /* Other default colors to override */
    //   background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    //   onTertiary =  Color(0xFF749FB8),
    //   onBackground = Color(0xFF1C1B1F),
    //   onSurface = Color(0xFF1C1B1F),

)

@Composable
fun VitesseHRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}