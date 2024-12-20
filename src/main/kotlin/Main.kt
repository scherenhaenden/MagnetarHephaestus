import UI.Main.MainView
import UI.Main.MainViewV2
import UI.Main.Theme.DarkColorPalette
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun App() {
    var currentVersion by remember { mutableStateOf(1) } // Track the current view

    // Cache the MainView composable using remember to retain its state
    val cachedMainView = remember { mutableStateOf<@Composable () -> Unit>({ MainView() }) }

    MaterialTheme(colors = DarkColorPalette) { // Apply dark theme globally
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("MagnetarHephaestus") },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    actions = {
                        Button(
                            onClick = {
                                currentVersion = if (currentVersion == 1) 2 else 1
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            )
                        ) {
                            Text("Switch to Version ${if (currentVersion == 1) "2" else "1"}")
                        }
                    }
                )
            }
        ) {
            when (currentVersion) {
                1 -> cachedMainView.value() // Use the cached MainView
                2 -> MainViewV2() // Display Version 2 without affecting MainView
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
