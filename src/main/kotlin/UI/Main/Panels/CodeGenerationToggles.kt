package UI.Main.Panels

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun CodeGenerationToggles(
    includeComments: Boolean,
    onIncludeCommentsChange: (Boolean) -> Unit,
    includeDecorators: Boolean,
    onIncludeDecoratorsChange: (Boolean) -> Unit,
    layerModels: Boolean,
    onLayerModelsChange: (Boolean) -> Unit
) {
    Column {
        Row {
            Checkbox(
                checked = layerModels,
                onCheckedChange = onLayerModelsChange
            )
            Text("Generate Layered Models")
        }

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Button(onClick = { onIncludeCommentsChange(!includeComments) }) {
                Text(if (includeComments) "Hide Comments" else "Show Comments")
            }
            Button(onClick = { onIncludeDecoratorsChange(!includeDecorators) }) {
                Text(if (includeDecorators) "Hide Decorators" else "Show Decorators")
            }
        }
    }
}