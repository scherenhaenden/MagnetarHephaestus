package UI.Main.Panels

import BusinessLogic.Models.APIModeler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RightPanelContent(apiModeler: APIModeler?) {
    var includeComments by remember { mutableStateOf(true) }
    var includeDecorators by remember { mutableStateOf(true) }
    var layerModels by remember { mutableStateOf(false) }

    if (apiModeler != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CodeGenerationToggles(
                includeComments = includeComments,
                onIncludeCommentsChange = { includeComments = it },
                includeDecorators = includeDecorators,
                onIncludeDecoratorsChange = { includeDecorators = it },
                layerModels = layerModels,
                onLayerModelsChange = { layerModels = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LanguageSelector(
                apiModeler = apiModeler,
                includeComments = includeComments,
                includeDecorators = includeDecorators,
                layerModels = layerModels
            )
        }
    } else {
        Text("No code to generate.")
    }
}

