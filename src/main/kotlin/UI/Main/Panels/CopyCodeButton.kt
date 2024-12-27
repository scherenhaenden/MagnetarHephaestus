package UI.Main.Panels

import BusinessLogic.Models.APIModeler
import Common.CodeGenerators.CodeGenerator
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.AnnotatedString

@Composable
fun CopyCodeButton(
    selectedLanguages: List<String>,
    generators: Map<String, CodeGenerator>,
    apiModeler: APIModeler,
    includeComments: Boolean,
    includeDecorators: Boolean,
    layerModels: Boolean,
    onCopy: (String) -> Unit
) {

    val clipboardManager = LocalClipboardManager.current


    Button(onClick = {
        val codeToCopy = buildString {
            selectedLanguages.forEach { language ->
                val generator = generators[language]
                if (generator != null) {
                    append("Generated $language Code:\n")
                    append(
                        generator.generateCode(
                            apiModeler,
                            includeComments,
                            includeDecorators,
                            layerModels
                        )
                    )
                    append("\n")
                }
            }
        }
        onCopy(codeToCopy)
        clipboardManager.setText(AnnotatedString(codeToCopy))
    }) {
        Text("Copy Code")
    }
}
