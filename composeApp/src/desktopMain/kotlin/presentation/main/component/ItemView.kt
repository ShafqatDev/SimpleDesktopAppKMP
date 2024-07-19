package presentation.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import domain.model.User
import dummylistkmp.composeapp.generated.resources.Res
import dummylistkmp.composeapp.generated.resources.dummy_user
import org.jetbrains.compose.resources.painterResource

@Composable
fun ItemView(user: User, onItemClick: (User) -> Unit, onDelete: (User) -> Unit) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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