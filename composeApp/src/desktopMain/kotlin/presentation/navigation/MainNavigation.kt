package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import domain.model.User
import myapp.LocalNavHostController
import presentation.component.Routes
import presentation.detail.DetailScreen
import presentation.main.MainScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    var users by remember { mutableStateOf(listOf<User>()) }

    CompositionLocalProvider(LocalNavHostController provides navController) {
        NavHost(navController, startDestination = Routes.MainScreen.name) {
            composable(Routes.MainScreen.name) {
                MainScreen(users, onAddUser =  { newUser ->
                    users = users + newUser
                }, onDelete = {hello->
                    users = users.filter { hello.id != it.id }
                })
            }

            composable("${Routes.DetailScreen.name}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                val user = users.find { it.id == id }
                user?.let {
                    DetailScreen(it, navController::navigateUp)
                }
            }
        }
    }
}