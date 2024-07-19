package presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.User
import dummylistkmp.composeapp.generated.resources.Res
import dummylistkmp.composeapp.generated.resources.dummy_user
import org.jetbrains.compose.resources.painterResource


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
