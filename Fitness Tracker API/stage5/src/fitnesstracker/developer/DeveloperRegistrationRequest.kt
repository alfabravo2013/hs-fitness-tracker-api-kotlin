package fitnesstracker.developer

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class DeveloperRegistrationRequest(
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
