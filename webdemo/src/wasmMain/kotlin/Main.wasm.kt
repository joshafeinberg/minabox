import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import eu.wewox.minabox.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() = CanvasBasedWindow("MinaBox") {
    App()
}
