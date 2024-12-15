package UI.Main.Panels

import BusinessLogic.Models.APIModeler
import Common.CodeGenerators.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageSelector(
    apiModeler: APIModeler,
    includeComments: Boolean,
    includeDecorators: Boolean,
    layerModels: Boolean
) {
    val selectedLanguages = remember { mutableStateListOf<String>() }
    val generators = CodeGeneratorFactory.getGenerators()
    var codeToCopy by remember { mutableStateOf("") }

    LanguageSelectionButtons(selectedLanguages, generators.keys)
    Spacer(modifier = Modifier.height(16.dp))

    GeneratedCodeDisplay(
        apiModeler = apiModeler,
        selectedLanguages = selectedLanguages,
        generators = generators,
        includeComments = includeComments,
        includeDecorators = includeDecorators,
        layerModels = layerModels
    )

    CopyCodeButton(
        selectedLanguages = selectedLanguages,
        generators = generators,
        apiModeler = apiModeler,
        includeComments = includeComments,
        includeDecorators = includeDecorators,
        layerModels = layerModels,
        onCopy = { codeToCopy = it }
    )
}

