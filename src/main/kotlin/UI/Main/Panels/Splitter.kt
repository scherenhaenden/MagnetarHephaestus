// Splitter.kt
package UI.Main.Panels

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Splitter(
    splitterPosition: Float,
    onSplitterPositionChange: (Float) -> Unit,
    parentWidthPx: Float
) {
    var isHovering by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(4.dp)
            .fillMaxHeight()
            .background(if (isHovering) Color.Gray else Color.DarkGray)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    val currentPixels = splitterPosition * parentWidthPx
                    val newPixels = currentPixels + dragAmount.x
                    val newFraction = (newPixels / parentWidthPx).coerceIn(0.1f, 0.9f)

                    onSplitterPositionChange(newFraction)
                    change.consume()
                }
            }
            .pointerMoveFilter(
                onEnter = { isHovering = true; false },
                onExit = { isHovering = false; false }
            )
            .pointerHoverIcon(PointerIcon.Hand)
    )
}
