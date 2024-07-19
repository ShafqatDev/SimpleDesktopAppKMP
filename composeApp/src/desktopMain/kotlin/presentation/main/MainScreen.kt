package presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.User
import myapp.LocalNavHostController
import presentation.main.component.ItemView
import presentation.main.component.ToastMessage
import java.util.Calendar

@Composable
fun MainScreen(
    users: List<User>,
    onAddUser: (User) -> Unit,
    onDelete: (User) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val navController = LocalNavHostController.current
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
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
                ItemView(user, onItemClick = {
                    navController.navigate("detail/${user.id}")
                }, onDelete = { delete ->
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