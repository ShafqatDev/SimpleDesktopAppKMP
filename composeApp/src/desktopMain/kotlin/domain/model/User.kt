package domain.model

import java.util.Calendar

data class User(
    val id: Long = Calendar.getInstance().timeInMillis,
    val name: String,
    val email: String
)