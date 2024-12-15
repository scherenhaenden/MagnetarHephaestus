package UI.Main.Panels

import BusinessLogic.Models.APIModeler
import Common.CodeGenerators.CodeGenerator
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneratedCodeDisplay(
    apiModeler: APIModeler,
    selectedLanguages: List<String>,
    generators: Map<String, CodeGenerator>,
    includeComments: Boolean,
    includeDecorators: Boolean,
    layerModels: Boolean
) {
    Column {
        selectedLanguages.forEach { language ->
            val generator = generators[language]
            if (generator != null) {
                Text("Generated $language Code:")
                val code = generator.generateCode(
                    apiModeler,
                    includeComments,
                    includeDecorators,
                    layerModels
                )
                Text(code)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
