package UI.Main.Panels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import BusinessLogic.Models.APIModeler
import Common.*

@Composable
fun RightPanelContent(apiModeler: APIModeler?) {
    // States for toggles
    var includeComments by remember { mutableStateOf(true) }
    var includeDecorators by remember { mutableStateOf(true) }

    // State to track which languages are selected
    val selectedLanguages = remember { mutableStateListOf<String>() }

    // Language Generators
    val generators = mapOf(
        "C#" to CodeGeneratorCSharp(),
        "TypeScript" to CodeGeneratorTypeScript(),
        "Kotlin" to CodeGeneratorKotlin(),
        "Python" to CodeGeneratorPython(),
        "Java" to CodeGeneratorJava()
    )

    if (apiModeler != null) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Buttons to toggle options
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { includeComments = !includeComments }) {
                    Text(if (includeComments) "Hide Comments" else "Show Comments")
                }
                Button(onClick = { includeDecorators = !includeDecorators }) {
                    Text(if (includeDecorators) "Hide Decorators" else "Show Decorators")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons to add/remove languages
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
                            if (selectedLanguages.contains(language))
                                "Remove $language"
                            else
                                "Add $language"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Scrollable code content
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
                selectedLanguages.forEach { language ->
                    val generator = generators[language]
                    if (generator != null) {
                        Text("Generated $language Code:", style = androidx.compose.ui.text.TextStyle.Default.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
                        val code = generator.generateCode(apiModeler, includeComments, includeDecorators)
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
