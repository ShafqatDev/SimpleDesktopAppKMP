import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dummylistkmp.composeapp.generated.resources.Res
import dummylistkmp.composeapp.generated.resources.dummy_user
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.util.Calendar

val LocalNavHostController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        var users by remember { mutableStateOf(listOf<User>()) }

        CompositionLocalProvider(LocalNavHostController provides navController) {
            NavHost(navController, startDestination = "list") {
                composable("list") {
                    Screen(users, onAddUser =  { newUser ->
                        users = users + newUser
                    }, onDelete = {hello->
                        users = users.filter { hello.id != it.id }
                    })
                }

                composable("detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                    val user = users.find { it.id == id }
                    user?.let {
                        DetailScreen(it, navController::navigateUp)
                    }
                }
            }
        }
    }
}

@Composable
fun Screen(users: List<User>, onAddUser: (User) -> Unit,onDelete: (User) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val navController = LocalNavHostController.current
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank()) {
                    onAddUser(
                        User(
                            id = Calendar.getInstance().timeInMillis,
                            name = name,
                            email = email
                        )
                    )
                    name = ""
                    email = ""
                } else {
                    toastMessage = "Enter Name And Gmail"
                    showToast = true
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(users) { user ->
                ItemView(user, onItemClick =  {
                    navController.navigate("detail/${user.id}")
                }, onDelete = {delete->
                    onDelete.invoke(delete)
                    toastMessage = "User Deleted"
                    showToast = true
                })
            }
        }
    }
    if (showToast) {
        ToastMessage(
            message = toastMessage,
            onDismiss = { showToast = false }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(user: User, goBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize().padding(16.dp)) {
            val painter = painterResource(Res.drawable.dummy_user)
            Box(
                modifier = Modifier.fillMaxWidth().height(300.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = user.name,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }

            Text(user.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ItemView(user: User, onItemClick: (User) -> Unit,onDelete: (User) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(user) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(Res.drawable.dummy_user), // Replace with your resource
                    contentDescription = "",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Column {
                    Text(text = user.name, style = MaterialTheme.typography.titleLarge)
                    Text(text = user.email, style = MaterialTheme.typography.titleLarge)
                }
            }
            IconButton(onClick = {
                onDelete.invoke(user)
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

data class User(
    val id: Long = Calendar.getInstance().timeInMillis,
    val name: String,
    val email: String
)

@Composable
fun ListScreen(
    items: List<User>,
    onItemClick: (User) -> Unit,
    onDelete: (User) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items, key = { user -> user.id }) { user ->
            ItemView(user, onItemClick,onDelete)
        }
    }
}


@Composable
fun ToastMessage(
    message: String,
    duration: Long = 3000L,
    onDismiss: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(duration)
        visible = false
        onDismiss()
    }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = message,
                    color = Color.White
                )
            }
        }
    }
}
