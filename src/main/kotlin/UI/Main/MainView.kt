package UI.Main

import BusinessLogic.Models.APIModeler
import Common.JsonHelper
import UI.Main.Panels.LeftPanelContent
import UI.Main.Panels.Panel
import UI.Main.Panels.RightPanelContent
import UI.Main.Panels.Splitter
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@Composable
@Preview
fun MainView() {
    var splitterPosition by remember { mutableStateOf(0.5f) }
    var leftPanelMinimized by remember { mutableStateOf(false) }
    var rightPanelMinimized by remember { mutableStateOf(false) }
    var parentWidthPx by remember { mutableStateOf<Float?>(null) }
    var inputText by remember { mutableStateOf("defaultJson") } // Initialize with defaultJson
    var apiModeler by remember { mutableStateOf<APIModeler?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            IconButton(onClick = { leftPanelMinimized = !leftPanelMinimized }) {
                Icon(
                    if (leftPanelMinimized) Icons.Filled.Add else Icons.Filled.Close,
                    contentDescription = if (leftPanelMinimized) "Maximize" else "Minimize"
                )
            }
            IconButton(onClick = { rightPanelMinimized = !rightPanelMinimized }) {
                Icon(
                    if (rightPanelMinimized) Icons.Filled.Add else Icons.Filled.Close,
                    contentDescription = if (rightPanelMinimized) "Maximize" else "Minimize"
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .onGloballyPositioned { coords ->
                    val size: Size = coords.size.toSize()
                    parentWidthPx = size.width
                }
        ) {
            Box(modifier = Modifier.weight(splitterPosition)) {
                Panel(
                    minimized = leftPanelMinimized,
                    onMinimizeToggle = { leftPanelMinimized = !leftPanelMinimized },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    LeftPanelContent(
                        initialInputText = inputText, // Pass inputText to LeftPanelContent
                        onInputChange = { inputText = it },
                        onCreateClasses = { input ->
                            apiModeler = JsonHelper.deserialize(input)
                        }
                    )
                }
            }

            if (parentWidthPx != null) {
                Splitter(
                    splitterPosition = splitterPosition,
                    onSplitterPositionChange = { newFraction -> splitterPosition = newFraction },
                    parentWidthPx = parentWidthPx!!
                )
            }

            Box(modifier = Modifier.weight(1f - splitterPosition)) {
                Panel(
                    minimized = rightPanelMinimized,
                    onMinimizeToggle = { rightPanelMinimized = !rightPanelMinimized },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    RightPanelContent(apiModeler)
                }
            }
        }
    }
}