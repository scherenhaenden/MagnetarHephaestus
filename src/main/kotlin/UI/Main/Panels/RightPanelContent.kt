package UI.Main.Panels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import BusinessLogic.Models.APIModeler
import Common.CodeGenerators.*

@Composable
fun RightPanelContent(apiModeler: APIModeler?) {
    // States for toggles
    var includeComments by remember { mutableStateOf(true) }
    var includeDecorators by remember { mutableStateOf(true) }
    var layerModels by remember { mutableStateOf(false) }
    var generateMappers by remember { mutableStateOf(false) }

    // State to track which languages are selected
    val selectedLanguages = remember { mutableStateListOf<String>() }

    // Language Generators
    val generators = mapOf(
        "C#" to CodeGeneratorCSharp(),
        "TypeScript" to CodeGeneratorTypeScript(),
        "Kotlin" to CodeGeneratorKotlin(),
        "Python" to CodeGeneratorPython(),
        "Java" to CodeGeneratorJava(),
        "MySQL" to CodeGeneratorMySQL(),
        "Go" to CodeGeneratorGo(),
        "C++" to CodeGeneratorCpp()
    )

    if (apiModeler != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            var codeToCopy by remember { mutableStateOf("") }

            Row {
                Checkbox(
                    checked = layerModels,
                    onCheckedChange = { layerModels = it }
                )
                Text("Generate Layered Models")
            }

            Row {
                Checkbox(
                    checked = generateMappers,
                    onCheckedChange = { generateMappers = it }
                )
                Text("Generate Mappers")
            }

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Button(onClick = { includeComments = !includeComments }) {
                    Text(if (includeComments) "Hide Comments" else "Show Comments")
                }
                Button(onClick = { includeDecorators = !includeDecorators }) {
                    Text(if (includeDecorators) "Hide Decorators" else "Show Decorators")
                }
                Button(onClick = {
                    codeToCopy = buildString {
                        selectedLanguages.forEach { language ->
                            val generator = generators[language]
                            if (generator != null) {
                                append("Generated $language Code:\n")
                                val code = generator.generateCode(
                                    apiModeler,
                                    includeComments,
                                    includeDecorators,
                                    layerModels,

                                )
                                append(code)
                                append("\n")
                            }
                        }
                    }
                    // Trigger clipboard copy action here (platform-specific)
                }) {
                    Text("Copy Code")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                generators.keys.forEach { language ->
                    Button(onClick = {
                        if (selectedLanguages.contains(language)) {
                            selectedLanguages.remove(language)
                        } else {
                            selectedLanguages.add(language)
                        }
                    }) {
                        Text(
                            if (selectedLanguages.contains(language)) "Remove $language" else "Add $language"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                selectedLanguages.forEach { language ->
                    val generator = generators[language]
                    if (generator != null) {
                        Text(
                            "Generated $language Code:",
                            style = androidx.compose.ui.text.TextStyle.Default.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        )
                        val code = generator.generateCode(
                            apiModeler,
                            includeComments,
                            includeDecorators,
                            layerModels,
                        )
                        Text(code)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    } else {
        Text("No code to generate.")
    }
}