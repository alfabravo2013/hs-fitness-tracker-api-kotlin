package fitnesstracker.application

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ApplicationRegistrationRequest(
    @field:NotBlank
    val name: String,

    @field:NotNull
    val description: String
)
