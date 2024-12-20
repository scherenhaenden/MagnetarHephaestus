import UI.Main.MainView
import UI.Main.MainViewV2
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun App() {
    var currentVersion by remember { mutableStateOf(1) } // State to track the current version

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("MagnetarHephaestus") },
                    actions = {
                        Button(onClick = {
                            // Toggle between Version 1 and Version 2
                            currentVersion = if (currentVersion == 1) 2 else 1
                        }) {
                            Text("Switch to Version ${if (currentVersion == 1) "2" else "1"}")
                        }
                    }
                )
            }
        ) {
            when (currentVersion) {
                1 -> MainView() // Version 1: The existing MainView
                2 -> MainViewV2() // Version 2: A new placeholder view
            }
        }
    }
}



fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
