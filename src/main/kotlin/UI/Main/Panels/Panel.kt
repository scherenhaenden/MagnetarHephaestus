package UI.Main.Panels

// Panel.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Panel(
    modifier: Modifier = Modifier,
    minimized: Boolean,
    onMinimizeToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (minimized) Color.Gray else Color.LightGray)
            .padding(if (minimized) 0.dp else 16.dp)
    ) {
        if (minimized) {
            Button(onClick = onMinimizeToggle, modifier = Modifier.fillMaxSize()) {
                Text("Maximize")
            }
        } else {
            content()
        }
    }
}
