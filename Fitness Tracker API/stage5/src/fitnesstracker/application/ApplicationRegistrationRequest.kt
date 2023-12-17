package fitnesstracker.application

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class ApplicationRegistrationRequest(
    @field:NotBlank
    val name: String,

    @field:NotNull
    val description: String,

    @field:NotBlank
    @field:Pattern(regexp = "(basic|premium)")
    val category: String
)
