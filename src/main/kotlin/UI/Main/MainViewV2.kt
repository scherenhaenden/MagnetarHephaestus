package UI.Main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainViewV2() {
    val availablePanels = listOf("Input Panel", "Output Panel", "Editable Panel") // Side menu options
    val panelVisibility = remember { mutableStateMapOf<String, Boolean>() } // Track panel visibility

    Row(modifier = Modifier.fillMaxSize()) {
        // Side Menu
        SideMenu(availablePanels = availablePanels) { panelType ->
            panelVisibility[panelType] = !(panelVisibility[panelType] ?: false) // Toggle visibility
        }

        // Main Workspace
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (panelVisibility.values.none { it }) {
                Text("No panels added. Use the side menu to add a panel.", style = MaterialTheme.typography.body1)
            } else {
                panelVisibility.filter { it.value }.keys.forEach { panelName ->
                    DynamicPanel(
                        name = panelName,
                        onRemove = { panelVisibility[panelName] = false } // Hide panel on removal
                    )
                }
            }
        }
    }
}

@Composable
fun SideMenu(availablePanels: List<String>, onPanelToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(80.dp) // Reduced width for compact design
            .padding(8.dp)
            .border(BorderStroke(1.dp, MaterialTheme.colors.onSurface))
    ) {
        Text(
            "Panels",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        availablePanels.forEach { panelType ->
            IconButton(
                onClick = { onPanelToggle(panelType) },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = when (panelType) {
                        "Input Panel" -> Icons.Default.Edit
                        "Output Panel" -> Icons.Default.Visibility
                        "Editable Panel" -> Icons.Default.Build
                        else -> Icons.Default.Help
                    },
                    contentDescription = panelType,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}



@Composable
fun DynamicPanel(name: String, onRemove: () -> Unit) {
    var isMinimized by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("Type here...") }

    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with Title and Minimize/Remove Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(name, style = MaterialTheme.typography.h6)
                Row {
                    IconButton(onClick = { isMinimized = !isMinimized }) {
                        Icon(
                            if (isMinimized) Icons.Default.Add else Icons.Default.Close,
                            contentDescription = if (isMinimized) "Expand" else "Collapse"
                        )
                    }
                    IconButton(onClick = onRemove) {
                        Icon(Icons.Default.Close, contentDescription = "Remove Panel")
                    }
                }
            }

            // Panel Content: Show only when not minimized
            if (!isMinimized) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Editable Input
                    Text("Input:", style = MaterialTheme.typography.subtitle1)
                    BasicTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(BorderStroke(1.dp, MaterialTheme.colors.onSurface))
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Editable Output
                    Text("Output:", style = MaterialTheme.typography.subtitle1)
                    BasicTextField(
                        value = "Output: $inputText",
                        onValueChange = {}, // Output is read-only
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(BorderStroke(1.dp, MaterialTheme.colors.onSurface))
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}


