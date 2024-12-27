package UI.Main.Panels

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



@Composable
fun LanguageSelectionDropdown(
    selectedLanguages: MutableList<String>,
    availableLanguages: Set<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Dropdown Button
        Button(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
            Text("Select Languages")
        }

        // Dropdown Menu
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            availableLanguages.forEach { language ->
                DropdownMenuItem(onClick = {
                    if (selectedLanguages.contains(language)) {
                        selectedLanguages.remove(language)
                    } else {
                        selectedLanguages.add(language)
                    }
                }) {
                    Text(
                        text = if (selectedLanguages.contains(language)) "Deselect $language" else "Select $language"
                    )
                }
            }
        }
    }
}



@Composable
fun LanguageSelectionPanel(
    selectedLanguages: MutableList<String>,
    availableLanguages: Set<String>,
    globalSetting: MutableState<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {


        // Mutable list to track selected languages
        val selectedLanguages = remember { mutableStateListOf<String>() }

        // Simulate available languages (replace with your actual set)
        val availableLanguages = setOf("C#", "TypeScript", "Kotlin", "Python", "Java")

        // Render the dropdown
        LanguageSelectionDropdown(
            selectedLanguages = selectedLanguages,
            availableLanguages = availableLanguages
        )

        // Global Settings Dropdown
        GlobalSettingsDropdown(globalSetting)

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons Row
        LanguageSelectionButtons(selectedLanguages, availableLanguages)

        Spacer(modifier = Modifier.height(16.dp))

        // Language-Specific Dropdowns
        availableLanguages.forEach { language ->
            LanguageDropdown(language, selectedLanguages, globalSetting.value)
        }
    }
}

@Composable
fun GlobalSettingsDropdown(globalSetting: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Global Setting: ${globalSetting.value}")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                globalSetting.value = "Add Decorators"
                expanded = false
            }) {
                Text("Add Decorators")
            }
            DropdownMenuItem(onClick = {
                globalSetting.value = "Hide Comments"
                expanded = false
            }) {
                Text("Hide Comments")
            }
        }
    }
}

@Composable
fun LanguageDropdown(
    language: String,
    selectedLanguages: MutableList<String>,
    globalSetting: String) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Language Dropdown Button
        Button(onClick = { expanded = !expanded }) {
            Text("$language Options")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                if (selectedLanguages.contains(language)) {
                    selectedLanguages.remove(language)
                } else {
                    selectedLanguages.add(language)
                }
                expanded = false
            }) {
                Text(if (selectedLanguages.contains(language)) "Remove $language" else "Add $language")
            }

            if (globalSetting == "Add Decorators") {
                DropdownMenuItem(onClick = { /* Add decorator logic */ }) {
                    Text("Add Decorators")
                }
            }

            if (globalSetting == "Hide Comments") {
                DropdownMenuItem(onClick = { /* Hide comments logic */ }) {
                    Text("Hide Comments")
                }
            }
        }
    }
}

@Composable
fun LanguageSelectionButtons(
    selectedLanguages: MutableList<String>,
    availableLanguages: Set<String>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        availableLanguages.forEach { language ->
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
}
