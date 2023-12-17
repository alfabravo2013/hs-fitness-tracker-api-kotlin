package fitnesstracker.developer

import fitnesstracker.application.ApplicationProfile

data class DeveloperProfile(
    val id: String,
    val email: String,
    val applications: List<ApplicationProfile>
)
