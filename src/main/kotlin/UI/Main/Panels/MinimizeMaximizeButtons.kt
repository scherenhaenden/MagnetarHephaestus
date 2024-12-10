package UI.Main.Panels

// MinimizeMaximizeButtons.kt
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MinimizeMaximizeButtons(
    leftPanelMinimized: Boolean,
    onLeftPanelMinimizeToggle: () -> Unit,
    rightPanelMinimized: Boolean,
    onRightPanelMinimizeToggle: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Button(onClick = onLeftPanelMinimizeToggle) {
            Text(if (leftPanelMinimized) "Maximize Left" else "Minimize Left")
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = onRightPanelMinimizeToggle) {
            Text(if (rightPanelMinimized) "Maximize Right" else "Minimize Right")
        }
    }
}