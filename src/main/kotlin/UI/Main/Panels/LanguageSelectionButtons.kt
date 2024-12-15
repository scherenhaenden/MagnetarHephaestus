package UI.Main.Panels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
