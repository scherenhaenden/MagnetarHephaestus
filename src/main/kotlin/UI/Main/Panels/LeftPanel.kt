package UI.Main.Panels

// LeftPanel.kt
import Common.FileTypeUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LeftPanel(
    modifier: Modifier = Modifier,
    leftPanelMinimized: Boolean,
    onLeftPanelMinimizeToggle: () -> Unit
) {
    var text by remember { mutableStateOf("Hello, World!") }
    var inputText by remember { mutableStateOf("") }
    var fileType by remember { mutableStateOf("Unknown") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (leftPanelMinimized) Color.Gray else Color.Blue)
            .padding(if (leftPanelMinimized) 0.dp else 16.dp)
    ) {
        if (!leftPanelMinimized) {
            Button(onClick = { text = "Hello, Desktop!" }) {
                Text(text)
            }

            TextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    fileType = FileTypeUtils.determineFileType(it)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            )
        } else {
            Button(onClick = onLeftPanelMinimizeToggle, modifier = Modifier.fillMaxSize()) {
                Text("Maximize")
            }
        }
    }
}