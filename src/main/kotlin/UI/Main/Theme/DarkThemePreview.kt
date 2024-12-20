package UI.Main.Theme

import UI.Main.MainViewV2
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define the Dark Color Palette
val DarkColorPalette = darkColors(
    primary = Color(0xFFBB86FC),       // Purple
    primaryVariant = Color(0xFF3700B3), // Dark Purple
    secondary = Color(0xFF03DAC5),      // Teal
    background = Color(0xFF121212),     // Dark background
    surface = Color(0xFF1E1E1E),        // Darker surface
    onPrimary = Color.White,            // Text on purple
    onSecondary = Color.Black,          // Text on teal
    onBackground = Color.Black,         // Text on background
    onSurface = Color.White             // Text on surface
)

// Preview to see how the Dark Theme looks with MainViewV2
@Preview()
@Composable
fun DarkThemePreview() {
    MaterialTheme(colors = DarkColorPalette) {
        MainViewV2() // Display MainViewV2 within the dark theme
    }
}


@Composable
fun MagnetarTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette, // Use the dark color palette
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content // Apply the theme to the content
    )
}