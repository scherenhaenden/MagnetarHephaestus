// RightPanel.kt
package UI.Main.Panels

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
fun RightPanel(
    modifier: Modifier = Modifier,
    rightPanelMinimized: Boolean,
    onRightPanelMinimizeToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (rightPanelMinimized) Color.Gray else Color.LightGray)
            .padding(if (rightPanelMinimized) 0.dp else 16.dp)
    ) {
        if (!rightPanelMinimized) {
            Text("Right Panel Content", color = Color.Black)
        } else {
            Button(onClick = onRightPanelMinimizeToggle, modifier = Modifier.fillMaxSize()) {
                Text("Maximize")
            }
        }
    }
}

