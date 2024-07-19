package myapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.navigation.MainNavigation

val LocalNavHostController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainNavigation()
    }
}
